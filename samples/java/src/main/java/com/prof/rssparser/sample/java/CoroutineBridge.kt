package com.prof.rssparser.sample.java

import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

fun parseFeed(parser: RssParser, url: String): CompletableFuture<RssChannel> = GlobalScope.future {
    parser.getRssChannel(url)
}
