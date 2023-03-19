package com.prof.rssparser.sample.common

import com.prof.rssparser.Parser
import com.prof.rssparser.build

object DI {
    val feedRepository = FeedRepository(
        parser = Parser.build()
    )
}
