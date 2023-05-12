package com.prof.rssparser.sample.common

import com.prof.rssparser.RssParser
import com.prof.rssparser.build

object DI {
    val feedRepository = FeedRepository(
        parser = RssParser.build()
    )
}
