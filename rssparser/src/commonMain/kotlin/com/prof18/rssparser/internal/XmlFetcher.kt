package com.prof18.rssparser.internal

internal interface XmlFetcher {
    suspend fun fetchXml(url: String): ParserInput
    fun generateParserInputFromString(rawRssFeed: String): ParserInput
}
