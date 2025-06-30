package com.prof18.rssparser.internal

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request

internal class WebXmlFetcher : XmlFetcher {

    private val client by lazy {
        HttpClient()
    }

    override suspend fun fetchXml(url: String): ParserInput =
        fetchFeed(url)

    override suspend fun fetchXmlAsString(url: String): String =
        fetchFeed(url).data

    private suspend fun fetchFeed(url: String): ParserInput {
        val response = client.get(url)
        val baseUrl = response.request.url.let {
            "${it.protocol.name}://${it.host}"
        }
        val responseAsString = response.bodyAsText()
        return ParserInput(
            data = responseAsString,
            baseUrl = baseUrl
        )
    }
}
