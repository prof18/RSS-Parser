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

    /**
     * Download and parses a Rss feed from an [url] and returns a [RssChannel].
     */
    suspend fun getRssChannel(url: String): RssChannel = withContext(coroutineContext) {
        val parserInput = xmlFetcher.fetchXml(url)
        return@withContext xmlParser.parseXML(parserInput)
    }

    /**
     * Parses a Rss feed provided by [rawRssFeed] and returns a [RssChannel]
     */
    suspend fun parse(rawRssFeed: String): RssChannel = withContext(coroutineContext) {
        val parserInput = xmlFetcher.generateParserInputFromString(rawRssFeed)
        return@withContext xmlParser.parseXML(parserInput)
    }

    companion object
}
