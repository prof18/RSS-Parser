package com.prof18.rssparser

import com.prof18.rssparser.internal.AndroidXmlParser
import com.prof18.rssparser.internal.JvmXmlFetcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient
import java.nio.charset.Charset


/**
 * A Builder that creates a new instance of [RssParser]
 *
 * @property callFactory A custom [OkHttpClient] that can be provided by outside.
 *  If not provided, the created `RssParser` will use a default one.
 * @property charset The [Charset] of the RSS feed. This field is optional. If nothing is provided,
 *  it will be inferred from the feed.
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
            xmlParser = AndroidXmlParser(
                charset = charset,
                dispatcher = Dispatchers.IO,
            ),
        )
    }
}

public actual fun RssParser(): RssParser = RssParserBuilder().build()
