package com.prof18.rssparser.internal

import com.prof18.rssparser.internal.atom.AtomFeedHandler
import com.prof18.rssparser.internal.rss.RssFeedHandler
import com.prof18.rssparser.model.RssChannel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import platform.Foundation.NSXMLParser
import platform.Foundation.NSXMLParserDelegateProtocol
import platform.darwin.NSObject

internal class IosXmlParser(
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {

    override suspend fun parseXML(input: ParserInput): RssChannel = withContext(dispatcher) {
        suspendCoroutine { continuation ->
            NSXMLParser(input.data).apply {
                delegate = NSXMLParserDelegate { continuation.resume(it) }
            }.parse()
        }
    }
}

private class NSXMLParserDelegate(
    private val onEnd: (RssChannel) -> Unit
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
}
