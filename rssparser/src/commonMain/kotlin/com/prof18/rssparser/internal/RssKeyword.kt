package com.prof18.rssparser.internal

internal enum class RssKeyword(val value: String) {
    // Root
    RSS("rss"),

    // Shared between Channel and Item
    TITLE("title"),
    IMAGE("image"),
    LINK("link"),
    HREF("href"),
    URL("url"),
    DESCRIPTION("description"),

    // iTunes (shared)
    ITUNES_AUTHOR("itunes:author"),
    ITUNES_DURATION("itunes:duration"),
    ITUNES_KEYWORDS("itunes:keywords"),
    ITUNES_IMAGE("itunes:image"),
    ITUNES_EXPLICIT("itunes:explicit"),
    ITUNES_SUBTITLE("itunes:subtitle"),
    ITUNES_SUMMARY("itunes:summary"),

    // Channel
    CHANNEL("channel"),
    CHANNEL_UPDATE_PERIOD("sy:updatePeriod"),
    CHANNEL_LAST_BUILD_DATE("lastBuildDate"),

    // Channel iTunes
    CHANNEL_ITUNES_CATEGORY("itunes:category"),
    CHANNEL_ITUNES_OWNER("itunes:owner"),
    CHANNEL_ITUNES_OWNER_NAME("itunes:name"),
    CHANNEL_ITUNES_OWNER_EMAIL("itunes:email"),
    CHANNEL_ITUNES_TYPE("itunes:type"),
    CHANNEL_ITUNES_NEW_FEED_URL("itunes:new-feed-url"),
    CHANNEL_ITUNES_TEXT("text"),

    // Item
    ITEM("item"),
    ITEM_AUTHOR("dc:creator"),
    ITEM_DATE("dc:date"),
    ITEM_CATEGORY("category"),
    ITEM_THUMBNAIL("media:thumbnail"),
    ITEM_MEDIA_CONTENT("media:content"),
    ITEM_ENCLOSURE("enclosure"),
    ITEM_CONTENT("content:encoded"),
    ITEM_PUB_DATE("pubDate"),
    ITEM_TIME("time"),
    ITEM_TYPE("type"),
    ITEM_LENGTH("length"),
    ITEM_GUID("guid"),
    ITEM_SOURCE("source"),
    ITEM_COMMENTS("comments"),
    ITEM_THUMB("thumb"),

    // Item News
    ITEM_NEWS_IMAGE("News:Image"),

    // Item iTunes
    ITEM_ITUNES_EPISODE("itunes:episode"),
    ITEM_ITUNES_SEASON("itunes:season"),
    ITEM_ITUNES_EPISODE_TYPE("itunes:episodeType");

    internal companion object {
        private val valueMap: Map<String, RssKeyword> = entries.associateBy { it.value.lowercase() }

        fun isValid(value: String): Boolean {
            return value.lowercase() in valueMap
        }
    }
}
