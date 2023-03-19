package com.prof.rssparser

import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURLSession

// TODO: or use the custom builder for better binary compatibility?
fun Parser.Companion.build(
    nsUrlSession: NSURLSession? = null,
): Parser {
    return Parser(
        xmlFetcher = IosXmlFetcher(
            nsUrlSession = nsUrlSession ?: NSURLSession.sharedSession,
        ),
        xmlParser = IosXmlParser(
            Dispatchers.Default
        ),
    )
}
