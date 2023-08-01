package com.prof18.rssparser.internal

import com.prof18.rssparser.ParsingException
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
import kotlin.coroutines.suspendCoroutine

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
                        continuation.resumeWithException(exception)
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
    private val onError: (ParsingException) -> Unit,
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
        val parsingException = ParsingException(
            nsError = parseErrorOccurred,
            message = parseErrorOccurred.localizedDescription,
            failureReason = parseErrorOccurred.localizedFailureReason,
            recoverySuggestion = parseErrorOccurred.localizedRecoverySuggestion,
        )
        onError(parsingException)
    }
}
