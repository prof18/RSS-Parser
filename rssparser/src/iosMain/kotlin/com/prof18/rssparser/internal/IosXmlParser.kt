package com.prof18.rssparser.internal

import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.internal.atom.AtomFeedHandler
import com.prof18.rssparser.internal.rss.RssFeedHandler
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSError
import platform.Foundation.NSInputStream
import platform.Foundation.NSXMLParser
import platform.Foundation.NSXMLParserDelegateProtocol
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class IosXmlParser(
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {

    override suspend fun parseXML(input: ParserInput): RssChannel = withContext(dispatcher) {
        suspendCancellableCoroutine { continuation ->
            val stream = NSInputStream(input.data)
            val parser = NSXMLParser(stream).apply {
                delegate = NSXMLParserDelegate(
                    onEnd = { rssChannel ->
                        continuation.resume(rssChannel)
                    },
                    onError = { exception ->
                        val error = RssParsingException(
                            message = "Something went wrong during the parsing of the feed. Please check if the XML is valid",
                            cause = exception
                        )
                        continuation.resumeWithException(error)
                    }
                )
            }
            parser.parse()

            continuation.invokeOnCancellation {
                parser.abortParsing()
            }
        }
    }
}

private class NSXMLParserDelegate(
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

            RssKeyword.Rss.value -> {
                feedHandler = RssFeedHandler()
            }

            AtomKeyword.Atom.value -> {
                feedHandler = AtomFeedHandler()
            }

            else -> {
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
        qualifiedName: String?
    ) {
        feedHandler?.didEndElement(didEndElement)
    }

    override fun parserDidEndDocument(parser: NSXMLParser) {
        val feedHandler = requireNotNull(feedHandler)
        onEnd(feedHandler.buildRssChannel())
    }

    override fun parser(parser: NSXMLParser, parseErrorOccurred: NSError) {
        val NSXMLParsingException = NSXMLParsingException(
            nsError = parseErrorOccurred,
            message = parseErrorOccurred.localizedDescription,
            failureReason = parseErrorOccurred.localizedFailureReason,
            recoverySuggestion = parseErrorOccurred.localizedRecoverySuggestion,
        )
        onError(NSXMLParsingException)
    }
}
