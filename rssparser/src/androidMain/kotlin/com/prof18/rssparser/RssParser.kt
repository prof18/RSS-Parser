package com.prof18.rssparser

import com.prof18.rssparser.internal.AndroidXmlParser
import com.prof18.rssparser.internal.JvmXmlFetcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient
import java.nio.charset.Charset

/**
 * Return a new instance of [RssParser]
 *
 * @param callFactory A custom [OkHttpClient] that can be provided by outside.
 *  If not provided, it will be created for you.
 * @param charset The [Charset] of the RSS feed. The field is optional; if nothing is provided,
 *  it will be inferred from the feed
 */
fun RssParser.Companion.build(
    callFactory: Call.Factory? = null,
    charset: Charset? = null,
): RssParser {
    val client = callFactory ?: OkHttpClient()
    return RssParser(
        xmlFetcher = JvmXmlFetcher(
            callFactory = client,
                charset = charset,
        ),
        xmlParser = AndroidXmlParser(
            charset = charset,
            dispatcher = Dispatchers.IO,
        ),
    )
}
