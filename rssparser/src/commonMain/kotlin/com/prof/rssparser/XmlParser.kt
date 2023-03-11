package com.prof.rssparser

interface XmlParser {
    suspend fun parseXML(input: ParserInput): Channel
}

