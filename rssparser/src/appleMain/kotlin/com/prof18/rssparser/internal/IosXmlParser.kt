package com.prof18.rssparser.internal

import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.internal.atom.AtomFeedHandler
import com.prof18.rssparser.internal.rdf.RdfFeedHandler
import com.prof18.rssparser.internal.rss.RssFeedHandler
import com.prof18.rssparser.model.RssChannel
import kotlinx.cinterop.BetaInteropApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSError
import platform.Foundation.NSInputStream
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSXMLParser
import platform.Foundation.NSXMLParserDelegateProtocol
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class IosXmlParser(
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {

    override suspend fun parseXML(input: ParserInput): RssChannel = withContext(dispatcher) {
        val stream = NSInputStream(input.data)
        val parser = NSXMLParser(stream)
        suspendCancellableCoroutine { continuation ->
            parser.delegate = NSXMLParserDelegate(
                baseFeedUrl = input.baseUrl,
                onEnd = { rssChannel ->
                    stream.close()
                    continuation.resume(rssChannel)
                },
                onError = { exception ->
                    val error = RssParsingException(
                        message = "Something went wrong when parsing the feed. Please check if the XML is valid",
                        cause = exception
                    )
                    stream.close()
                    continuation.resumeWithException(error)
                }
            )
            parser.parse()

            continuation.invokeOnCancellation {
                parser.abortParsing()
                stream.close()
            }
        }
    }

    @OptIn(BetaInteropApi::class)
    override fun generateParserInputFromString(rawRssFeed: String): ParserInput {
        val cleanedXml = rawRssFeed.trim()
        val data = NSString.create(string = cleanedXml).dataUsingEncoding(NSUTF8StringEncoding)
        return ParserInput(requireNotNull(data))
    }
}

private class NSXMLParserDelegate(
    private val baseFeedUrl: String?,
    private val onEnd: (RssChannel) -> Unit,
    private val onError: (NSXMLParsingException) -> Unit,
) : NSObject(), NSXMLParserDelegateProtocol {

    private var feedHandler: FeedHandler? = null

    override fun parser(
        parser: NSXMLParser,
        didStartElement: String,
        namespaceURI: String?,
        qualifiedName: String?,
        attributes: Map<Any?, *>,
    ) {
        when (didStartElement) {
            RssKeyword.RSS.value -> {
                feedHandler = RssFeedHandler()
            }

            AtomKeyword.ATOM.value -> {
                feedHandler = AtomFeedHandler(baseFeedUrl)
            }

            RdfKeyword.RDF.value -> {
                feedHandler = RdfFeedHandler()
            }

            else -> {
                if (feedHandler == null) {
                    throw IllegalArgumentException(
                        "The provided XML is not supported. Only RSS and Atom feeds are supported",
                    )
                }
                feedHandler?.didStartElement(didStartElement, attributes)
            }
        }
    }

    override fun parser(parser: NSXMLParser, foundCharacters: String) {
        feedHandler?.foundCharacters(foundCharacters)
    }

    override fun parser(
        parser: NSXMLParser,
        didEndElement: String,
        namespaceURI: String?,
        qualifiedName: String?,
    ) {
        feedHandler?.didEndElement(didEndElement)
    }

    override fun parserDidEndDocument(parser: NSXMLParser) {
        val feedHandler = requireNotNull(feedHandler)
        onEnd(feedHandler.buildRssChannel())
    }

    override fun parser(parser: NSXMLParser, parseErrorOccurred: NSError) {
        val nSXMLParsingException = NSXMLParsingException(
            nsError = parseErrorOccurred,
            message = parseErrorOccurred.localizedDescription,
            failureReason = parseErrorOccurred.localizedFailureReason,
            recoverySuggestion = parseErrorOccurred.localizedRecoverySuggestion,
        )
        parser.abortParsing()
        onError(nSXMLParsingException)
    }
}
