package com.prof.rssparser

data class ItunesArticleData(
    val image: String? = null,
    val duration: String? = null,
    val explicit: String? = null,
    val keywords: List<String> = emptyList(),
    val subtitle: String? = null,
    val episode: String? = null,
    val episodeType: String? = null,
    val author: String? = null,
    val summary: String? = null,
)

data class ItunesChannelData(
    val explicit: String? = null,
    val type: String? = null,
    val subtitle: String? = null,
    val author: String? = null,
    val summary: String? = null,
    val image: String? = null,
    val category: List<String> = emptyList(),
    val newsFeedUrl: String? = null,
    val owner: ItunesOwner? = null
)

data class ItunesOwner(
    val name: String? = null,
    val email: String? = null,
)