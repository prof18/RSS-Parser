package com.prof18.rssparser

import com.prof18.rssparser.internal.JvmXmlFetcher
import com.prof18.rssparser.internal.JvmXmlParser
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient
import java.nio.charset.Charset


/**
 * A Builder that creates a new instance of [RssParser]
 *
 * @property callFactory A custom [OkHttpClient] that can be provided by outside.
 *  If not provided, it will be created for you.
 * @property charset The [Charset] of the RSS feed. The field is optional; if nothing is provided,
 *  it will be inferred from the feed
 */
public class RssParserBuilder(
    private val callFactory: Call.Factory = OkHttpClient(),
    private val charset: Charset? = null,
): RssParser.Builder {
    override fun build(): RssParser {
        return RssParser(
            xmlFetcher = JvmXmlFetcher(
                callFactory = callFactory,
            ),
            xmlParser = JvmXmlParser(
                charset = charset,
                dispatcher = Dispatchers.IO,
            ),
        )
    }
}

public actual fun RssParser(): RssParser = RssParserBuilder().build()
