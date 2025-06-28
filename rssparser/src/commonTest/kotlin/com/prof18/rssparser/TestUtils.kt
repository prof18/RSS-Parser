package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import com.prof18.rssparser.model.RssChannel

internal expect fun readFileFromResources(
    resourceName: String
): ParserInput

internal expect fun readFileFromResourcesAsString(
    resourceName: String
): String

internal expect val currentTarget: CurrentTarget

internal const val BASE_FEED_URL = "https://www.base-feed-url.com"

internal suspend fun parseFeed(
    feedPath: String,
): RssChannel {
    val input = readFileFromResources(feedPath)
    return createXmlParser().parseXML(input)
}
