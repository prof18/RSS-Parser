package com.prof18.rssparser

import com.prof18.rssparser.internal.IosXmlFetcher
import com.prof18.rssparser.internal.IosXmlParser
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURLSession

// TODO: write doc
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
