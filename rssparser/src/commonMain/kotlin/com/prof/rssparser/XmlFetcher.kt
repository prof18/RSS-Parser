package com.prof.rssparser

internal interface XmlFetcher {
    suspend fun fetchXml(url: String): ParserInput
}
