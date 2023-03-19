package com.prof.rssparser.sample.common

data class Feed(
    val title: String,
    val items: List<FeedItem>
)

data class FeedItem(
    val title: String,
    val subtitle: String?,
    val content: String?,
    val imageUrl: String?,
    val dateString: String,
)
