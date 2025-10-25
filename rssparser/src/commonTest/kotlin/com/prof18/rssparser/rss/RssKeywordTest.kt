package com.prof18.rssparser.rss

import com.prof18.rssparser.internal.RssKeyword
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RssKeywordTest {

    @Test
    fun fromValue_shouldReturnCorrectKeyword_whenValidValueProvided() {
        // Root
        assertEquals(RssKeyword.RSS, RssKeyword.fromValue("rss"))

        // Shared keywords
        assertEquals(RssKeyword.TITLE, RssKeyword.fromValue("title"))
        assertEquals(RssKeyword.IMAGE, RssKeyword.fromValue("image"))
        assertEquals(RssKeyword.LINK, RssKeyword.fromValue("link"))
        assertEquals(RssKeyword.HREF, RssKeyword.fromValue("href"))
        assertEquals(RssKeyword.URL, RssKeyword.fromValue("url"))
        assertEquals(RssKeyword.DESCRIPTION, RssKeyword.fromValue("description"))

        // iTunes shared
        assertEquals(RssKeyword.ITUNES_AUTHOR, RssKeyword.fromValue("itunes:author"))
        assertEquals(RssKeyword.ITUNES_DURATION, RssKeyword.fromValue("itunes:duration"))
        assertEquals(RssKeyword.ITUNES_KEYWORDS, RssKeyword.fromValue("itunes:keywords"))
        assertEquals(RssKeyword.ITUNES_IMAGE, RssKeyword.fromValue("itunes:image"))
        assertEquals(RssKeyword.ITUNES_EXPLICIT, RssKeyword.fromValue("itunes:explicit"))
        assertEquals(RssKeyword.ITUNES_SUBTITLE, RssKeyword.fromValue("itunes:subtitle"))
        assertEquals(RssKeyword.ITUNES_SUMMARY, RssKeyword.fromValue("itunes:summary"))

        // Channel
        assertEquals(RssKeyword.CHANNEL, RssKeyword.fromValue("channel"))
        assertEquals(RssKeyword.CHANNEL_UPDATE_PERIOD, RssKeyword.fromValue("sy:updatePeriod"))
        assertEquals(RssKeyword.CHANNEL_LAST_BUILD_DATE, RssKeyword.fromValue("lastBuildDate"))

        // Channel iTunes
        assertEquals(RssKeyword.CHANNEL_ITUNES_CATEGORY, RssKeyword.fromValue("itunes:category"))
        assertEquals(RssKeyword.CHANNEL_ITUNES_OWNER, RssKeyword.fromValue("itunes:owner"))
        assertEquals(RssKeyword.CHANNEL_ITUNES_OWNER_NAME, RssKeyword.fromValue("itunes:name"))
        assertEquals(RssKeyword.CHANNEL_ITUNES_OWNER_EMAIL, RssKeyword.fromValue("itunes:email"))
        assertEquals(RssKeyword.CHANNEL_ITUNES_TYPE, RssKeyword.fromValue("itunes:type"))
        assertEquals(RssKeyword.CHANNEL_ITUNES_NEW_FEED_URL, RssKeyword.fromValue("itunes:new-feed-url"))
        assertEquals(RssKeyword.CHANNEL_ITUNES_TEXT, RssKeyword.fromValue("text"))

        // Item
        assertEquals(RssKeyword.ITEM, RssKeyword.fromValue("item"))
        assertEquals(RssKeyword.ITEM_AUTHOR, RssKeyword.fromValue("dc:creator"))
        assertEquals(RssKeyword.ITEM_DATE, RssKeyword.fromValue("dc:date"))
        assertEquals(RssKeyword.ITEM_CATEGORY, RssKeyword.fromValue("category"))
        assertEquals(RssKeyword.ITEM_THUMBNAIL, RssKeyword.fromValue("media:thumbnail"))
        assertEquals(RssKeyword.ITEM_MEDIA_CONTENT, RssKeyword.fromValue("media:content"))
        assertEquals(RssKeyword.ITEM_ENCLOSURE, RssKeyword.fromValue("enclosure"))
        assertEquals(RssKeyword.ITEM_CONTENT, RssKeyword.fromValue("content:encoded"))
        assertEquals(RssKeyword.ITEM_PUB_DATE, RssKeyword.fromValue("pubDate"))
        assertEquals(RssKeyword.ITEM_TIME, RssKeyword.fromValue("time"))
        assertEquals(RssKeyword.ITEM_TYPE, RssKeyword.fromValue("type"))
        assertEquals(RssKeyword.ITEM_LENGTH, RssKeyword.fromValue("length"))
        assertEquals(RssKeyword.ITEM_GUID, RssKeyword.fromValue("guid"))
        assertEquals(RssKeyword.ITEM_SOURCE, RssKeyword.fromValue("source"))
        assertEquals(RssKeyword.ITEM_COMMENTS, RssKeyword.fromValue("comments"))
        assertEquals(RssKeyword.ITEM_THUMB, RssKeyword.fromValue("thumb"))

        // Item News
        assertEquals(RssKeyword.ITEM_NEWS_IMAGE, RssKeyword.fromValue("News:Image"))

        // Item iTunes
        assertEquals(RssKeyword.ITEM_ITUNES_EPISODE, RssKeyword.fromValue("itunes:episode"))
        assertEquals(RssKeyword.ITEM_ITUNES_SEASON, RssKeyword.fromValue("itunes:season"))
        assertEquals(RssKeyword.ITEM_ITUNES_EPISODE_TYPE, RssKeyword.fromValue("itunes:episodeType"))
    }

    @Test
    fun fromValue_shouldBeCaseInsensitive() {
        assertEquals(RssKeyword.TITLE, RssKeyword.fromValue("TITLE"))
        assertEquals(RssKeyword.TITLE, RssKeyword.fromValue("Title"))
        assertEquals(RssKeyword.DESCRIPTION, RssKeyword.fromValue("DESCRIPTION"))
        assertEquals(RssKeyword.ITEM_PUB_DATE, RssKeyword.fromValue("PUBDATE"))
        assertEquals(RssKeyword.ITUNES_AUTHOR, RssKeyword.fromValue("ITUNES:AUTHOR"))
    }

    @Test
    fun fromValue_shouldReturnNull_whenInvalidValueProvided() {
        assertNull(RssKeyword.fromValue("invalid"))
        assertNull(RssKeyword.fromValue("notAKeyword"))
        assertNull(RssKeyword.fromValue(""))
        assertNull(RssKeyword.fromValue("random"))
    }

    @Test
    fun isValid_shouldReturnTrue_whenValidValueProvided() {
        assertTrue(RssKeyword.isValid("rss"))
        assertTrue(RssKeyword.isValid("title"))
        assertTrue(RssKeyword.isValid("channel"))
        assertTrue(RssKeyword.isValid("item"))
        assertTrue(RssKeyword.isValid("pubDate"))
        assertTrue(RssKeyword.isValid("dc:creator"))
        assertTrue(RssKeyword.isValid("itunes:author"))
        assertTrue(RssKeyword.isValid("itunes:episode"))
        assertTrue(RssKeyword.isValid("content:encoded"))
    }

    @Test
    fun isValid_shouldReturnFalse_whenInvalidValueProvided() {
        assertFalse(RssKeyword.isValid("invalid"))
        assertFalse(RssKeyword.isValid("notAKeyword"))
        assertFalse(RssKeyword.isValid(""))
        assertFalse(RssKeyword.isValid("random"))
    }

    @Test
    fun isValid_shouldBeCaseInsensitive() {
        assertTrue(RssKeyword.isValid("TITLE"))
        assertTrue(RssKeyword.isValid("Title"))
        assertTrue(RssKeyword.isValid("DESCRIPTION"))
        assertTrue(RssKeyword.isValid("PUBDATE"))
        assertTrue(RssKeyword.isValid("ITUNES:AUTHOR"))
    }

    @Test
    fun enumValues_shouldContainAllExpectedKeywords() {
        val values = RssKeyword.entries
        // We expect 51 unique keywords in total
        assertEquals(51, values.size)

        // Verify critical keywords exist
        assertNotNull(values.find { it == RssKeyword.RSS })
        assertNotNull(values.find { it == RssKeyword.CHANNEL })
        assertNotNull(values.find { it == RssKeyword.ITEM })
        assertNotNull(values.find { it == RssKeyword.TITLE })
        assertNotNull(values.find { it == RssKeyword.ITUNES_AUTHOR })
        assertNotNull(values.find { it == RssKeyword.ITEM_PUB_DATE })
    }

    @Test
    fun valueProperty_shouldReturnCorrectStringValue() {
        assertEquals("rss", RssKeyword.RSS.value)
        assertEquals("title", RssKeyword.TITLE.value)
        assertEquals("image", RssKeyword.IMAGE.value)
        assertEquals("link", RssKeyword.LINK.value)
        assertEquals("href", RssKeyword.HREF.value)
        assertEquals("url", RssKeyword.URL.value)
        assertEquals("description", RssKeyword.DESCRIPTION.value)
        assertEquals("itunes:author", RssKeyword.ITUNES_AUTHOR.value)
        assertEquals("itunes:duration", RssKeyword.ITUNES_DURATION.value)
        assertEquals("itunes:keywords", RssKeyword.ITUNES_KEYWORDS.value)
        assertEquals("itunes:image", RssKeyword.ITUNES_IMAGE.value)
        assertEquals("itunes:explicit", RssKeyword.ITUNES_EXPLICIT.value)
        assertEquals("itunes:subtitle", RssKeyword.ITUNES_SUBTITLE.value)
        assertEquals("itunes:summary", RssKeyword.ITUNES_SUMMARY.value)
        assertEquals("channel", RssKeyword.CHANNEL.value)
        assertEquals("sy:updatePeriod", RssKeyword.CHANNEL_UPDATE_PERIOD.value)
        assertEquals("lastBuildDate", RssKeyword.CHANNEL_LAST_BUILD_DATE.value)
        assertEquals("itunes:category", RssKeyword.CHANNEL_ITUNES_CATEGORY.value)
        assertEquals("itunes:owner", RssKeyword.CHANNEL_ITUNES_OWNER.value)
        assertEquals("itunes:name", RssKeyword.CHANNEL_ITUNES_OWNER_NAME.value)
        assertEquals("itunes:email", RssKeyword.CHANNEL_ITUNES_OWNER_EMAIL.value)
        assertEquals("itunes:type", RssKeyword.CHANNEL_ITUNES_TYPE.value)
        assertEquals("itunes:new-feed-url", RssKeyword.CHANNEL_ITUNES_NEW_FEED_URL.value)
        assertEquals("text", RssKeyword.CHANNEL_ITUNES_TEXT.value)
        assertEquals("item", RssKeyword.ITEM.value)
        assertEquals("dc:creator", RssKeyword.ITEM_AUTHOR.value)
        assertEquals("dc:date", RssKeyword.ITEM_DATE.value)
        assertEquals("category", RssKeyword.ITEM_CATEGORY.value)
        assertEquals("media:thumbnail", RssKeyword.ITEM_THUMBNAIL.value)
        assertEquals("media:content", RssKeyword.ITEM_MEDIA_CONTENT.value)
        assertEquals("enclosure", RssKeyword.ITEM_ENCLOSURE.value)
        assertEquals("content:encoded", RssKeyword.ITEM_CONTENT.value)
        assertEquals("pubDate", RssKeyword.ITEM_PUB_DATE.value)
        assertEquals("time", RssKeyword.ITEM_TIME.value)
        assertEquals("type", RssKeyword.ITEM_TYPE.value)
        assertEquals("length", RssKeyword.ITEM_LENGTH.value)
        assertEquals("guid", RssKeyword.ITEM_GUID.value)
        assertEquals("source", RssKeyword.ITEM_SOURCE.value)
        assertEquals("comments", RssKeyword.ITEM_COMMENTS.value)
        assertEquals("thumb", RssKeyword.ITEM_THUMB.value)
        assertEquals("News:Image", RssKeyword.ITEM_NEWS_IMAGE.value)
        assertEquals("itunes:episode", RssKeyword.ITEM_ITUNES_EPISODE.value)
        assertEquals("itunes:season", RssKeyword.ITEM_ITUNES_SEASON.value)
        assertEquals("itunes:episodeType", RssKeyword.ITEM_ITUNES_EPISODE_TYPE.value)
    }
}
