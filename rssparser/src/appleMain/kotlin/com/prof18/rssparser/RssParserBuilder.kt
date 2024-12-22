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
public class RssParserBuilder(
    private val nsUrlSession: NSURLSession = NSURLSession.sharedSession,
): RssParser.Builder {
    override fun build(): RssParser {
        return RssParser(
            xmlFetcher = IosXmlFetcher(
                nsUrlSession = nsUrlSession,
            ),
            xmlParser = IosXmlParser(
                Dispatchers.Default
            ),
        )
    }
}

public actual fun RssParser(): RssParser = RssParserBuilder().build()
