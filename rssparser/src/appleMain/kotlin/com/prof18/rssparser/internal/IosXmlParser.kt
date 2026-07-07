package com.prof18.rssparser.internal

import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.internal.atom.AtomFeedHandler
import com.prof18.rssparser.internal.rdf.RdfFeedHandler
import com.prof18.rssparser.internal.rss.RssFeedHandler
import com.prof18.rssparser.model.RssChannel
import kotlinx.cinterop.BetaInteropApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSXMLParser
import platform.Foundation.NSXMLParserDelegateProtocol
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.darwin.NSObject

internal class IosXmlParser(
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {

    override suspend fun parseXML(input: ParserInput): RssChannel = withContext(dispatcher) {
        val delegate = NSXMLParserDelegate(baseFeedUrl = input.baseUrl)
        val parser = NSXMLParser(input.data)
        parser.delegate = delegate
        // NSXMLParser.parse() is synchronous: it returns only when parsing is done or aborted,
        // so the delegate outcome can be read right after. Resuming a coroutine (or throwing)
        // from inside a delegate callback is not safe: the parser is still running inside
        // libxml2 at that point.
        parser.parse()
        when (val result = delegate.result) {
            is ParsingResult.Success -> result.rssChannel
            is ParsingResult.Error -> throw RssParsingException(
                message = "Something went wrong when parsing the feed. Please check if the XML is valid",
                cause = result.exception,
            )
            null -> throw RssParsingException(
                message = "Something went wrong when parsing the feed. Please check if the XML is valid",
                cause = null,
            )
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
) : NSObject(), NSXMLParserDelegateProtocol {

    var result: ParsingResult? = null
        private set

    private var feedHandler: FeedHandler? = null
    private val textBuilder: StringBuilder = StringBuilder()

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
                val handler = feedHandler
                if (handler == null) {
                    failParsing(
                        parser = parser,
                        exception = IllegalArgumentException(
                            "The provided XML is not supported. Only RSS and Atom feeds are supported",
                        ),
                    )
                    return
                }
                runHandlerSafely(parser) {
                    // Clear text builder only for known RSS/Atom/RDF tags
                    // Don't clear for HTML tags within content to handle mismatched tag cases
                    if (handler.shouldClearTextBuilder(didStartElement)) {
                        textBuilder.clear()
                    }
                    handler.didStartElement(didStartElement, attributes)
                }
            }
        }
    }

    override fun parser(parser: NSXMLParser, foundCharacters: String) {
        textBuilder.append(foundCharacters)
    }

    override fun parser(
        parser: NSXMLParser,
        didEndElement: String,
        namespaceURI: String?,
        qualifiedName: String?,
    ) {
        runHandlerSafely(parser) {
            val text = textBuilder.toString().trim()
            feedHandler?.didEndElement(didEndElement, text)
        }
    }

    override fun parserDidEndDocument(parser: NSXMLParser) {
        val handler = feedHandler
        if (handler == null) {
            failParsing(
                parser = parser,
                exception = IllegalArgumentException(
                    "The provided XML is not supported. Only RSS and Atom feeds are supported",
                ),
            )
            return
        }
        runHandlerSafely(parser) {
            result = ParsingResult.Success(handler.buildRssChannel())
        }
    }

    override fun parser(parser: NSXMLParser, parseErrorOccurred: NSError) {
        // abortParsing() also reports here (NSXMLParserDelegateAbortedParseError),
        // so keep the first recorded outcome instead of overwriting it.
        if (result == null) {
            result = ParsingResult.Error(
                NSXMLParsingException(
                    nsError = parseErrorOccurred,
                    message = parseErrorOccurred.localizedDescription,
                    failureReason = parseErrorOccurred.localizedFailureReason,
                    recoverySuggestion = parseErrorOccurred.localizedRecoverySuggestion,
                ),
            )
        }
    }

    // A Kotlin exception must never unwind through the libxml2/NSXMLParser C frames
    // that invoke the delegate callbacks: record the failure and abort instead.
    private inline fun runHandlerSafely(parser: NSXMLParser, block: () -> Unit) {
        try {
            block()
        } catch (e: Throwable) {
            failParsing(parser = parser, exception = e)
        }
    }

    private fun failParsing(parser: NSXMLParser, exception: Throwable) {
        if (result == null) {
            result = ParsingResult.Error(exception)
        }
        parser.abortParsing()
    }
}

private sealed interface ParsingResult {
    data class Success(val rssChannel: RssChannel) : ParsingResult
    data class Error(val exception: Throwable) : ParsingResult
}
