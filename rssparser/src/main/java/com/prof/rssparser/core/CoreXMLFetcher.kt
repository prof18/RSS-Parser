package com.prof.rssparser.core

import okhttp3.Call
import okhttp3.Request
import java.nio.charset.Charset

internal object CoreXMLFetcher {
    @Throws(Exception::class)
    fun fetchXML(url: String, callFactory: Call.Factory, charset: Charset): String {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = callFactory.newCall(request).execute()
        val byteStream = response.body?.byteStream()
        return byteStream?.bufferedReader(charset).use { it?.readText() ?: ""}
    }
}
