package com.prof.rssparser

import com.prof.rssparser.internal.AndroidXmlParser
import com.prof.rssparser.internal.JvmXmlFetcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient
import java.nio.charset.Charset

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
        xmlParser = AndroidXmlParser(
            charset = charset,
            dispatcher = Dispatchers.IO,
        ),
    )
}
