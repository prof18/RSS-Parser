package com.prof.rssparser.internal

internal interface XmlFetcher {
    suspend fun fetchXml(url: String): ParserInput
}
