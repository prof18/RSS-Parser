package com.prof.rssparser.core

import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

object CoreXMLFetcher {

    @Throws(Exception::class)
    fun fetchXML(url: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        return response.body()!!.string()
    }

}