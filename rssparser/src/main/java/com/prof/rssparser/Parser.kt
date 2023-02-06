/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof.rssparser

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.prof.rssparser.caching.CacheManager
import com.prof.rssparser.core.CoreXMLParser
import com.prof.rssparser.enginecoroutine.CoroutineEngine
import com.prof.rssparser.engineforjava.XMLFetcher
import com.prof.rssparser.engineforjava.XMLParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.OkHttpClient
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class Parser private constructor(
    private var callFactory: Call.Factory,
    private val charset: Charset? = null,
    context: Context? = null,
    cacheExpirationMillis: Long? = null,
) {

    // private variables
    private var onComplete: OnTaskCompleted? = null
    private lateinit var service: ExecutorService

    // Internal to make testable
    internal var cacheManager: CacheManager? = null
    internal var executorService: ExecutorService? = null

    private val parserJob = SupervisorJob()
    private val coroutineContext: CoroutineContext
        get() = parserJob + Dispatchers.Default

    /**
     * Builds the Parser.
     *
     * The caching feature is NOT available if you use the [execute] method.
     *
     * @param callFactory A custom okHttpClient that can be provided by outside. If not provided, it will be created for you.
     * @param charset The charset of the RSS feed. The field is optional.
     * @param context Android Context that you must provide to make the caching mechanism work. If not provided, the caching will not work
     * @param cacheExpirationMillis The duration of the cache in milliseconds. If not provided, the caching will not work
     *
     */
    data class Builder(
        private var callFactory: Call.Factory? = null,
        private var charset: Charset? = null,
        private var context: Context? = null,
        private var cacheExpirationMillis: Long? = null,
    ) {
        fun okHttpClient(okHttpClient: OkHttpClient) = apply { this.callFactory = okHttpClient }
        fun callFactory(callFactory: Call.Factory) = apply { this.callFactory = callFactory }
        fun charset(charset: Charset) = apply { this.charset = charset }
        fun context(context: Context) = apply { this.context = context }
        fun cacheExpirationMillis(cacheExpirationMillis: Long) =
            apply { this.cacheExpirationMillis = cacheExpirationMillis }

        fun build(): Parser {
            val callFactory = if (this.callFactory != null) {
                this.callFactory!!
            } else {
                OkHttpClient()
            }
            return Parser(callFactory, charset, context, cacheExpirationMillis)
        }
    }

    init {
        if (context != null && cacheExpirationMillis != null) {
            cacheManager = CacheManager.getInstance(context, cacheExpirationMillis)
        }
    }

    /**
     * This method provides back-support for Java code.
     *
     * Provides the [onComplete] callback that will be called after a successful or a failed parsing
     *
     */
    fun onFinish(onComplete: OnTaskCompleted) {
        this.onComplete = onComplete
    }

    /**
     *
     * This method provides back-support for Java code.
     *
     * When the parsing it's successfully completed, the [OnTaskCompleted.onTaskCompleted] callback
     * is called with the parsed [Channel] as parameter.
     *
     * When there is an error on the parsing, the [OnTaskCompleted.onError] callback is called
     * with the [Exception] as parameter.
     *
     * If the [onComplete] variable is not initialized with the [onFinish] method, results are not
     * delivered upwards.
     *
     * This method DOES NOT provide caching support
     *
     * @param url The url of the RSS feed
     *
     */
    fun execute(url: String) {
        // For testing purpose
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor()
        }
        executorService!!.submit {
            service = Executors.newFixedThreadPool(2)
            val f1 = service.submit(XMLFetcher(url, callFactory))
            try {
                val rssFeed = f1.get()
                val f2 = service.submit(XMLParser(rssFeed, charset))
                onComplete?.onTaskCompleted(f2.get())
            } catch (e: Exception) {
                onComplete?.onError(e)
            } finally {
                service.shutdown()
            }
        }
    }

    /**
     *  Cancel the execution of the fetching and the parsing.
     */
    fun cancel() {
        if (::service.isInitialized) {
            // The client is using Java!
            try {
                if (!service.isShutdown) {
                    service.shutdownNow()
                }
            } catch (e: Exception) {
                onComplete?.onError(e)
            }
        } else {
            // The client is using Kotlin and coroutines
            if (coroutineContext.isActive) {
                coroutineContext.cancel()
            }
        }
    }

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
    suspend fun getChannel(url: String): Channel = withContext(coroutineContext) {
        // If the charset is null, then "null" is saved in the database.
        // It's easier for retrieving data afterwards
        val charsetString = charset.toString()
        val cachedFeed = cacheManager?.getCachedFeed(url, charsetString)
        if (cachedFeed != null) {
            Log.d(TAG, "Returning object from cache")
            return@withContext cachedFeed
        } else {
            Log.d(TAG, "Returning data from network")
            val xml = CoroutineEngine.fetchXML(url, callFactory)
            val channel = CoroutineEngine.parseXML(xml, charset)
            cacheManager?.cacheFeed(
                url = url,
                channel = channel,
                charset = charsetString,
            )
            return@withContext channel
        }
    }

    /**
     * Parses the [rawRssFeed] into a [Channel].
     *
     * @exception Exception if something goes wrong during the parsing of the RSS feed.
     * @param rawRssFeed The Raw data of the Rss Feed.
     */
    suspend fun parse(rawRssFeed: String): Channel =
        CoroutineEngine.parseXMLFromString(rawRssFeed, charset)

    /**
     * Parses the [rawRssFeed] into a [Channel] and notifies the [listener].
     *
     * If the operation is successful, then [OnTaskCompleted.onTaskCompleted] is called with
     * the parsed channel. Otherwise, [OnTaskCompleted.onError]  is called.
     *
     * @exception Exception if something goes wrong during the parsing of the RSS feed.
     * @param rawRssFeed The Raw data of the Rss Feed.
     * @param listener Completion listener
     */
    @WorkerThread
    fun parse(rawRssFeed: String, listener: OnTaskCompleted) {
        try {
            // The default for byteInputStream is Charsets.UTF_8
            val stream = rawRssFeed.byteInputStream(charset ?: Charsets.UTF_8)
            listener.onTaskCompleted(CoreXMLParser.parseXML(stream, charset))
        } catch (exception: Exception) {
            listener.onError(exception)
        }
    }

    /**
     *
     * Flush the cache for the provided url
     *
     * @param url The url of the RSS Feed
     *
     */
    suspend fun flushCache(url: String) {
        cacheManager?.flushCachedFeed(url)
    }

    internal companion object {
        private const val TAG = "RSSParser"
    }
}

