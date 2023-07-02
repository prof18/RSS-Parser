package com.prof18.rssparser

import com.prof18.rssparser.internal.IosXmlFetcher
import com.prof18.rssparser.internal.IosXmlParser
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURLSession

/**
 * Return a new instance of [RssParser]
 *
 * @param nsUrlSession A custom [NSURLSession] that can be provided by outside.
 *  If not provided, it will be created for you.
 */
fun RssParser.Companion.build(
    nsUrlSession: NSURLSession? = null,
): RssParser {
    return RssParser(
        xmlFetcher = IosXmlFetcher(
            nsUrlSession = nsUrlSession ?: NSURLSession.sharedSession,
        ),
        xmlParser = IosXmlParser(
            Dispatchers.Default
        ),
    )
}
