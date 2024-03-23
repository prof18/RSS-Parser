package com.prof18.rssparser.sample.common

import com.prof18.rssparser.RssParserBuilder


object DI {
    val feedRepository = FeedRepository(
        parser = RssParserBuilder().build()
    )
}
