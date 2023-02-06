package com.prof.rssparser.enginecoroutine

import com.prof.rssparser.Channel
import com.prof.rssparser.core.CoreXMLFetcher
import com.prof.rssparser.core.CoreXMLParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import java.io.InputStream
import java.nio.charset.Charset

internal object CoroutineEngine {

    suspend fun fetchXML(
        url: String,
        callFactory: Call.Factory,
    ): InputStream = withContext(Dispatchers.IO) {
        return@withContext CoreXMLFetcher.fetchXMLSuspendable(url, callFactory)
    }

    suspend fun parseXML(xmlStream: InputStream, charset: Charset?): Channel = withContext(Dispatchers.Default) {
        return@withContext CoreXMLParser.parseXML(xmlStream, charset)
    }

    suspend fun parseXMLFromString(xml: String, charset: Charset?): Channel = withContext(Dispatchers.Default) {
        val inputStream: InputStream = xml.byteInputStream(charset ?: Charsets.UTF_8)
        return@withContext CoreXMLParser.parseXML(inputStream, charset)
    }
}

