package com.prof.rssparser

import com.prof.rssparser.internal.IosXmlFetcher
import com.prof.rssparser.internal.IosXmlParser
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURLSession

// TODO: or use the custom builder for better binary compatibility?
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
