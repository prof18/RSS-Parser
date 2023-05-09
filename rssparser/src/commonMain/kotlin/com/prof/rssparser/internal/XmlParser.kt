package com.prof.rssparser.internal

import com.prof.rssparser.model.Channel

internal interface XmlParser {
    suspend fun parseXML(input: ParserInput): Channel
}
