package com.prof18.rssparser.sample.common

import com.prof18.rssparser.RssParser
import com.prof18.rssparser.build

object DI {
    val feedRepository = FeedRepository(
        parser = RssParser.build()
    )
}
