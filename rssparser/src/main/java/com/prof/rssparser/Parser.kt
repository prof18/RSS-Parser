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
import com.prof.rssparser.caching.CacheManager
import com.prof.rssparser.engine.XMLFetcher
import com.prof.rssparser.engine.XMLParser
import com.prof.rssparser.enginecoroutine.CoroutineEngine
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class Parser private constructor(private var okHttpClient: OkHttpClient? = null,
                                 private val charset: Charset = Charsets.UTF_8,
                                 context: Context? = null,
                                 cacheExpirationMillis: Long? = null
) {

    // Internal variables
    private lateinit var onComplete: OnTaskCompleted
    private lateinit var service: ExecutorService
    // Internal to make testable
    internal var cacheManager: CacheManager? = null

    private val parserJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parserJob + Dispatchers.Default

    // Constructor stuff

    // Just for back compatibility
    @Deprecated("Use the builder to create the parser object")
    constructor(okHttpClient: OkHttpClient? = null) : this(okHttpClient, Charsets.UTF_8, null, null)

    /**
     * Builds the Parser. TODO: make some example about the parser
     *
     * @param okHttpClient A custom okHttpClient that can be provided by outside. If not provided, it will be created for you.
     * @param charset The charset of the RSS feed. If not provided, [Charsets.UTF_8] will be used.
     * @param context Android Context that you must provide to make the caching mechanism work. If not provided, the caching will not work
     * @param cacheExpirationMillis The duration of the cache in milliseconds. If not provided, the caching will not work
     *
     */
    data class Builder(
            private var okHttpClient: OkHttpClient? = null,
            private var charset: Charset = Charsets.UTF_8,
            private var context: Context? = null,
            private var cacheExpirationMillis: Long? = null
    ) {

        fun okHttpClient(okHttpClient: OkHttpClient) = apply { this.okHttpClient = okHttpClient }
        fun charset(charset: Charset) = apply { this.charset = charset }
        fun context(context: Context) = apply { this.context = context }
        fun cacheExpirationMillis(cacheExpirationMillis: Long) = apply { this.cacheExpirationMillis = cacheExpirationMillis }
        fun build() = Parser(okHttpClient, charset, context, cacheExpirationMillis)
    }

    init {
        if (context != null && cacheExpirationMillis != null) {
            cacheManager = CacheManager.getInstance(context, cacheExpirationMillis)
        }
    }


    // TODO: add doc
    // TODO: test it
    fun onFinish(onComplete: OnTaskCompleted) {
        this.onComplete = onComplete
    }

    // TODO: add doc
    // TODO: add caching? Maybe no for the type being
    // TODO: test it!
    fun execute(url: String) {
        Executors.newSingleThreadExecutor().submit {
            service = Executors.newFixedThreadPool(2)
            val f1 = service.submit(XMLFetcher(url, okHttpClient, charset))
            try {
                val rssFeed = f1.get()
                val f2 = service.submit(XMLParser(rssFeed))
                onComplete.onTaskCompleted(f2.get())
            } catch (e: Exception) {
                onComplete.onError(e)
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
                onComplete.onError(e)
            }
        } else {
            // The client is using Kotlin and coroutines
            if (coroutineContext.isActive) {
                coroutineContext.cancel()
            }
        }
    }

    // TODO: add doc
    // TODO: test it
    @Throws(Exception::class)
    suspend fun getChannel(url: String): Channel = withContext(coroutineContext) {
        val cachedFeed = cacheManager?.getCachedFeed(url)
        if (cachedFeed != null) {
            Log.d(TAG, "Returning object from cache")
            return@withContext cachedFeed as Channel
        } else {
            Log.d(TAG, "Returning data from network")
            val xml = CoroutineEngine.fetchXML(url, okHttpClient, charset)
            val channel = CoroutineEngine.parseXML(xml)
            cacheManager?.cacheFeed(url, channel)
            return@withContext channel
        }
    }

//    suspend fun flushCache(url: String) {
//
//    }

    companion object {
        private const val TAG = "RSSParser"
    }

}

