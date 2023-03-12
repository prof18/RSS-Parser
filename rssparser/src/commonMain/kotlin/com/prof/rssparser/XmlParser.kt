package com.prof.rssparser

internal interface XmlParser {
    suspend fun parseXML(input: ParserInput): Channel
}

