package com.prof18.rssparser.internal

internal enum class AtomKeyword(val value: String) {
    // Root/Feed
    ATOM("feed"),
    TITLE("title"),
    ICON("icon"),
    SUBTITLE("subtitle"),
    UPDATED("updated"),

    // Link
    LINK("link"),
    LINK_HREF("href"),
    LINK_REL("rel"),
    LINK_REL_ALTERNATE("alternate"),
    LINK_REL_ENCLOSURE("enclosure"),
    LINK_REL_REPLIES("replies"),
    LINK_EDIT("edit"),
    LINK_SELF("self"),

    // Entry/Item
    ENTRY_ITEM("entry"),
    ENTRY_GUID("id"),
    ENTRY_CONTENT("content"),
    ENTRY_PUBLISHED("published"),
    ENTRY_CATEGORY("category"),
    ENTRY_TERM("term"),
    ENTRY_DESCRIPTION("summary"),
    ENTRY_AUTHOR("name"),
    ENTRY_EMAIL("email"),

    // YouTube
    YOUTUBE_CHANNEL_ID("yt:channelId"),
    YOUTUBE_VIDEO_ID("yt:videoId"),
    YOUTUBE_MEDIA_GROUP("media:group"),
    YOUTUBE_MEDIA_GROUP_TITLE("media:title"),
    YOUTUBE_MEDIA_GROUP_CONTENT("media:content"),
    YOUTUBE_MEDIA_GROUP_CONTENT_URL("url"),
    YOUTUBE_MEDIA_GROUP_THUMBNAIL("media:thumbnail"),
    YOUTUBE_MEDIA_GROUP_THUMBNAIL_URL("url"),
    YOUTUBE_MEDIA_GROUP_DESCRIPTION("media:description"),
    YOUTUBE_MEDIA_GROUP_COMMUNITY("media:community"),
    YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING("media:starRating"),
    YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING_COUNT("count"),
    YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS("media:statistics"),
    YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS_VIEWS("views");

    internal companion object {
        private val valueMap: Map<String, AtomKeyword> = entries.associateBy { it.value.lowercase() }

        fun isValid(value: String): Boolean {
            return value.lowercase() in valueMap
        }
    }
}
