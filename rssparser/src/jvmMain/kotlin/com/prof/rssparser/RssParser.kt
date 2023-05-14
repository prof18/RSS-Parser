package com.prof.rssparser

import com.prof.rssparser.internal.JvmXmlFetcher
import com.prof.rssparser.internal.JvmXmlParser
import java.nio.charset.Charset
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient

fun RssParser.Companion.build(
    callFactory: Call.Factory? = null,
    okHttpClient: OkHttpClient? = null,
    charset: Charset? = null,
): RssParser {
    val client = when {
        callFactory != null -> callFactory
        okHttpClient != null -> okHttpClient
        else -> OkHttpClient()
    }
    return RssParser(
        xmlFetcher = JvmXmlFetcher(
            callFactory = client,
        ),
        xmlParser = JvmXmlParser(
            charset = charset,
            dispatcher = Dispatchers.IO,
        ),
    )
}
