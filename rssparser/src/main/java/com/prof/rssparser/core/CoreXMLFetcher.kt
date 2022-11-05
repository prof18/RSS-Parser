package com.prof.rssparser.core

import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.nio.charset.Charset

internal object CoreXMLFetcher {
    fun fetchXML(url: String, okHttpClient: OkHttpClient? = null, charset: Charset): String {
        var client = okHttpClient
        if (client == null) {
            client = OkHttpClient()
        }
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        val byteStream = response.body?.byteStream()
        return byteStream?.bufferedReader(charset).use { it?.readText() ?: ""}
    }
}
