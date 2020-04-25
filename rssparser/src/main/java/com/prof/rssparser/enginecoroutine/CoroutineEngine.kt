package com.prof.rssparser.enginecoroutine

import com.prof.rssparser.core.CoreXMLFetcher
import com.prof.rssparser.core.CoreXMLParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.nio.charset.Charset

object CoroutineEngine {

    @Throws(Exception::class)
    suspend fun fetchXML(url: String, okHttpClient: OkHttpClient?, charset: Charset) =
            withContext(Dispatchers.IO) {
                return@withContext CoreXMLFetcher.fetchXML(url, okHttpClient, charset)
            }

    @Throws(Exception::class)
    suspend fun parseXML(xml: String) =
            withContext(Dispatchers.IO) {
                return@withContext CoreXMLParser.parseXML(xml)
            }
}

