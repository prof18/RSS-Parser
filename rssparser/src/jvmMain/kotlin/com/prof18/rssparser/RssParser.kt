package com.prof18.rssparser

import com.prof18.rssparser.internal.JvmXmlFetcher
import com.prof18.rssparser.internal.JvmXmlParser
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient
import java.nio.charset.Charset

// TODO: write doc
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
            charset = charset,
        ),
        xmlParser = JvmXmlParser(
            charset = charset,
            dispatcher = Dispatchers.IO,
        ),
    )
}
