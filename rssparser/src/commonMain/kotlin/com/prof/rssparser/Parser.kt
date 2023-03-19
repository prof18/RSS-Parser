package com.prof.rssparser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

// TODO: maybe change the name
class Parser internal constructor(
    private val xmlFetcher: XmlFetcher,
    private val xmlParser: XmlParser,
) {

    private val parserJob = SupervisorJob()
    private val coroutineContext: CoroutineContext
        get() = parserJob + Dispatchers.Default


    /**
     * Returns a parsed RSS [Channel].
     *
     * If the context and the cacheExpirationMillis has been provided with the [Builder.context]
     * and the [Builder.cacheExpirationMillis], the caching support is enabled. Before making a network
     * request, the method checks if there is a valid cached [Channel].
     *
     * @exception Exception if something goes wrong during the fetching or in the parsing of the RSS feed.
     * @param url The url of the RSS feed
     *
     */

    @Throws(Throwable::class)
    suspend fun getChannel(url: String): Channel = withContext(coroutineContext) {
        // If the charset is null, then "null" is saved in the database.
        // It's easier for retrieving data afterwards

        val parserInput = xmlFetcher.fetchXml(url)
        val channel = xmlParser.parseXML(parserInput)

        return@withContext channel

//        val charsetString = charset.toString()
//        val cachedFeed = cacheManager?.getCachedFeed(url, charsetString)
//        if (cachedFeed != null) {
//            Log.d(TAG, "Returning object from cache")
//            return@withContext cachedFeed
//        } else {
//            Log.d(TAG, "Returning data from network")
//            val xml = CoroutineEngine.fetchXML(url, callFactory)
//            val channel = CoroutineEngine.parseXML(xml, charset)
//            cacheManager?.cacheFeed(
//                url = url,
//                channel = channel,
//                charset = charsetString,
//            )
//            return@withContext channel
//        }
    }

    /**
     *  Cancel the execution of the fetching and the parsing.
     */
    fun cancel() {
        if (coroutineContext.isActive) {
            coroutineContext.cancel()
        }
    }

    companion object

}