package com.prof.rssparser.internal

import com.prof.rssparser.model.RssChannel

internal interface XmlParser {
    suspend fun parseXML(input: ParserInput): RssChannel
}
