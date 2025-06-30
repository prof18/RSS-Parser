package com.prof18.rssparser

import com.prof18.rssparser.internal.WebXmlFetcher
import com.prof18.rssparser.internal.WebXmlParser
import kotlinx.coroutines.Dispatchers

public class RssParserBuilder : RssParser.Builder {
    override fun build(): RssParser {
        return RssParser(
            xmlFetcher = WebXmlFetcher(),
            xmlParser = WebXmlParser(
                dispatcher = Dispatchers.Default,
            )
        )
    }
}

public actual fun RssParser(): RssParser = RssParserBuilder().build()
