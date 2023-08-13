package com.prof18.rssparser

import com.prof18.rssparser.internal.IosXmlFetcher
import com.prof18.rssparser.internal.IosXmlParser
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURLSession

/**
 * A builder that creates new instances of [RssParser]
 *
 * @property nsUrlSession A custom [NSURLSession] that can be provided by the caller.
 *  If not provided, the created `RssParser` will use the shared session.
 */
class RssParserBuilder(
    private val nsUrlSession: NSURLSession? = null,
): RssParser.Builder {
    override fun build(): RssParser {
        return RssParser(
            xmlFetcher = IosXmlFetcher(
                nsUrlSession = nsUrlSession ?: NSURLSession.sharedSession,
            ),
            xmlParser = IosXmlParser(
                Dispatchers.Default
            ),
        )
    }
}

actual fun RssParser(): RssParser = RssParserBuilder().build()
