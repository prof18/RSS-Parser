package com.prof18.rssparser

import com.prof18.rssparser.internal.XmlFetcher
import com.prof18.rssparser.internal.XmlParser
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RssParser internal constructor(
    private val xmlFetcher: XmlFetcher,
    private val xmlParser: XmlParser,
) {

    private val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Default

    // TODO: rewrite KDoc
    /**
     * Returns a parsed RSS [RssChannel].
     *
     * If the context and the cacheExpirationMillis has been provided with the [Builder.context]
     * and the [Builder.cacheExpirationMillis], the caching support is enabled. Before making a network
     * request, the method checks if there is a valid cached [RssChannel].
     *
     * @exception Exception if something goes wrong during the fetching or in the parsing of the RSS feed.
     * @param url The url of the RSS feed
     *
     */
    // TODO: write doc
    @Throws(Throwable::class)
    suspend fun getRssChannel(url: String): RssChannel = withContext(coroutineContext) {
        val parserInput = xmlFetcher.fetchXml(url)
        return@withContext xmlParser.parseXML(parserInput)
    }

    // TODO: write doc
    suspend fun parse(rawRssFeed: String): RssChannel = withContext(coroutineContext) {
        val parserInput = xmlFetcher.generateParserInputFromString(rawRssFeed)
        return@withContext xmlParser.parseXML(parserInput)
    }

    companion object
}