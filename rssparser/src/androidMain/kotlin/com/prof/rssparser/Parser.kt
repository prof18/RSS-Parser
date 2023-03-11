package com.prof.rssparser

import okhttp3.Call
import okhttp3.OkHttpClient
import java.nio.charset.Charset

// TODO: or use the custom builder for better binary compatibility?
fun Parser.Companion.build(
    callFactory: Call.Factory? = null,
    okHttpClient: OkHttpClient? = null,
    charset: Charset? = null,
): Parser {
    val client = when {
        callFactory != null -> callFactory
        okHttpClient != null -> okHttpClient
        else -> OkHttpClient()
    }
    return Parser(
        xmlFetcher = JvmXmlFetcher(
            callFactory = client,
        ),
        xmlParser = AndroidXmlParser(
            charset = charset,
        ),
    )
}
