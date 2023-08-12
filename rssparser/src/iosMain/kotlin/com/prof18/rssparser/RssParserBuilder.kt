package com.prof18.rssparser

import com.prof18.rssparser.internal.IosXmlFetcher
import com.prof18.rssparser.internal.IosXmlParser
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURLSession

/**
 * A Builder that creates a new instance of [RssParser]
 *
 * @property nsUrlSession A custom [NSURLSession] that can be provided by outside.
 *  If not provided, it will be created for you.
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
