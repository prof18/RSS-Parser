package com.prof.rssparser

interface XmlFetcher {
    suspend fun fetchXml(url: String): ParserInput
}
