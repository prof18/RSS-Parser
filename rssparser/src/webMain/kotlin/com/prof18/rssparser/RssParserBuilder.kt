package com.prof18.rssparser

import com.prof18.rssparser.internal.WebXmlFetcher
import com.prof18.rssparser.internal.WebXmlParser
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers

/**
 * A builder that creates new instances of [RssParser]
 *
 * @property httpClient A custom [HttpClient] that can be provided by the caller.
 *  If not provided, the created `RssParser` will use a default one.
 */
public class RssParserBuilder(
    private val httpClient: HttpClient = HttpClient(),
) : RssParser.Builder {
    override fun build(): RssParser {
        return RssParser(
            xmlFetcher = WebXmlFetcher(
                client = httpClient,
            ),
            xmlParser = WebXmlParser(
                dispatcher = Dispatchers.Default,
            )
        )
    }
}

public actual fun RssParser(): RssParser = RssParserBuilder().build()
