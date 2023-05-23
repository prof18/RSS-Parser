package com.prof18.rssparser.sample.common

import com.prof18.rssparser.RssParser

class FeedRepository(
    private val parser: RssParser,
) {
    @Throws(Throwable::class)
    suspend fun getFeed(url: String): Feed {
        val channel = parser.getRssChannel(url)
        return Feed(
            title = channel.title ?: "",
            items = channel.items.mapNotNull {

                val title = it.title
                val subtitle = it.description
                val pubDate = it.pubDate

                if (title == null || subtitle == null || pubDate == null) {
                    return@mapNotNull null
                }

                FeedItem(
                    title = title,
                    subtitle = subtitle,
                    content = it.content,
                    imageUrl = it.image,
                    dateString = pubDate,
                )
            }
        )
    }
}
