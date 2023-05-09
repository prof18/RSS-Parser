package com.prof.rssparser.internal

import com.prof.rssparser.internal.ParserInput

internal interface XmlFetcher {
    suspend fun fetchXml(url: String): ParserInput
}
