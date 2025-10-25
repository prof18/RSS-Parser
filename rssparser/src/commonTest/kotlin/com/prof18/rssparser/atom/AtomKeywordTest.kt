package com.prof18.rssparser.atom

import com.prof18.rssparser.internal.AtomKeyword
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AtomKeywordTest {

    @Test
    fun fromValue_shouldReturnCorrectKeyword_whenValidValueProvided() {
        // Test feed/root keywords
        assertEquals(AtomKeyword.ATOM, AtomKeyword.fromValue("feed"))
        assertEquals(AtomKeyword.TITLE, AtomKeyword.fromValue("title"))
        assertEquals(AtomKeyword.ICON, AtomKeyword.fromValue("icon"))
        assertEquals(AtomKeyword.SUBTITLE, AtomKeyword.fromValue("subtitle"))
        assertEquals(AtomKeyword.UPDATED, AtomKeyword.fromValue("updated"))

        // Test link keywords
        assertEquals(AtomKeyword.LINK, AtomKeyword.fromValue("link"))
        assertEquals(AtomKeyword.LINK_HREF, AtomKeyword.fromValue("href"))
        assertEquals(AtomKeyword.LINK_REL, AtomKeyword.fromValue("rel"))
        assertEquals(AtomKeyword.LINK_REL_ALTERNATE, AtomKeyword.fromValue("alternate"))
        assertEquals(AtomKeyword.LINK_REL_ENCLOSURE, AtomKeyword.fromValue("enclosure"))
        assertEquals(AtomKeyword.LINK_REL_REPLIES, AtomKeyword.fromValue("replies"))
        assertEquals(AtomKeyword.LINK_EDIT, AtomKeyword.fromValue("edit"))
        assertEquals(AtomKeyword.LINK_SELF, AtomKeyword.fromValue("self"))

        // Test entry keywords
        assertEquals(AtomKeyword.ENTRY_ITEM, AtomKeyword.fromValue("entry"))
        assertEquals(AtomKeyword.ENTRY_GUID, AtomKeyword.fromValue("id"))
        assertEquals(AtomKeyword.ENTRY_CONTENT, AtomKeyword.fromValue("content"))
        assertEquals(AtomKeyword.ENTRY_PUBLISHED, AtomKeyword.fromValue("published"))
        assertEquals(AtomKeyword.ENTRY_CATEGORY, AtomKeyword.fromValue("category"))
        assertEquals(AtomKeyword.ENTRY_TERM, AtomKeyword.fromValue("term"))
        assertEquals(AtomKeyword.ENTRY_DESCRIPTION, AtomKeyword.fromValue("summary"))
        assertEquals(AtomKeyword.ENTRY_AUTHOR, AtomKeyword.fromValue("name"))
        assertEquals(AtomKeyword.ENTRY_EMAIL, AtomKeyword.fromValue("email"))

        // Test YouTube keywords
        assertEquals(AtomKeyword.YOUTUBE_CHANNEL_ID, AtomKeyword.fromValue("yt:channelId"))
        assertEquals(AtomKeyword.YOUTUBE_VIDEO_ID, AtomKeyword.fromValue("yt:videoId"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP, AtomKeyword.fromValue("media:group"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_TITLE, AtomKeyword.fromValue("media:title"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT, AtomKeyword.fromValue("media:content"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT_URL, AtomKeyword.fromValue("url"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL, AtomKeyword.fromValue("media:thumbnail"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_DESCRIPTION, AtomKeyword.fromValue("media:description"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY, AtomKeyword.fromValue("media:community"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING, AtomKeyword.fromValue("media:starRating"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING_COUNT, AtomKeyword.fromValue("count"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS, AtomKeyword.fromValue("media:statistics"))
        assertEquals(AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS_VIEWS, AtomKeyword.fromValue("views"))
    }

    @Test
    fun fromValue_shouldBeCaseInsensitive() {
        assertEquals(AtomKeyword.TITLE, AtomKeyword.fromValue("TITLE"))
        assertEquals(AtomKeyword.TITLE, AtomKeyword.fromValue("Title"))
        assertEquals(AtomKeyword.SUBTITLE, AtomKeyword.fromValue("SUBTITLE"))
        assertEquals(AtomKeyword.ENTRY_CONTENT, AtomKeyword.fromValue("CONTENT"))
        assertEquals(AtomKeyword.LINK_REL_ALTERNATE, AtomKeyword.fromValue("ALTERNATE"))
    }

    @Test
    fun fromValue_shouldReturnNull_whenInvalidValueProvided() {
        assertNull(AtomKeyword.fromValue("invalid"))
        assertNull(AtomKeyword.fromValue("notAKeyword"))
        assertNull(AtomKeyword.fromValue(""))
        assertNull(AtomKeyword.fromValue("random"))
    }

    @Test
    fun isValid_shouldReturnTrue_whenValidValueProvided() {
        assertTrue(AtomKeyword.isValid("feed"))
        assertTrue(AtomKeyword.isValid("title"))
        assertTrue(AtomKeyword.isValid("icon"))
        assertTrue(AtomKeyword.isValid("subtitle"))
        assertTrue(AtomKeyword.isValid("updated"))
        assertTrue(AtomKeyword.isValid("link"))
        assertTrue(AtomKeyword.isValid("entry"))
        assertTrue(AtomKeyword.isValid("yt:channelId"))
        assertTrue(AtomKeyword.isValid("media:group"))
        assertTrue(AtomKeyword.isValid("media:statistics"))
    }

    @Test
    fun isValid_shouldReturnFalse_whenInvalidValueProvided() {
        assertFalse(AtomKeyword.isValid("invalid"))
        assertFalse(AtomKeyword.isValid("notAKeyword"))
        assertFalse(AtomKeyword.isValid(""))
        assertFalse(AtomKeyword.isValid("random"))
    }

    @Test
    fun isValid_shouldBeCaseInsensitive() {
        assertTrue(AtomKeyword.isValid("TITLE"))
        assertTrue(AtomKeyword.isValid("Title"))
        assertTrue(AtomKeyword.isValid("SUBTITLE"))
        assertTrue(AtomKeyword.isValid("ENTRY"))
        assertTrue(AtomKeyword.isValid("YT:CHANNELID"))
    }

    @Test
    fun enumValues_shouldContainAllExpectedKeywords() {
        val values = AtomKeyword.entries
        // We expect 38 unique keywords in total
        assertEquals(38, values.size)

        // Verify critical keywords exist
        assertNotNull(values.find { it == AtomKeyword.ATOM })
        assertNotNull(values.find { it == AtomKeyword.TITLE })
        assertNotNull(values.find { it == AtomKeyword.ENTRY_ITEM })
        assertNotNull(values.find { it == AtomKeyword.LINK })
        assertNotNull(values.find { it == AtomKeyword.YOUTUBE_CHANNEL_ID })
    }

    @Test
    fun valueProperty_shouldReturnCorrectStringValue() {
        assertEquals("feed", AtomKeyword.ATOM.value)
        assertEquals("title", AtomKeyword.TITLE.value)
        assertEquals("icon", AtomKeyword.ICON.value)
        assertEquals("subtitle", AtomKeyword.SUBTITLE.value)
        assertEquals("updated", AtomKeyword.UPDATED.value)
        assertEquals("link", AtomKeyword.LINK.value)
        assertEquals("href", AtomKeyword.LINK_HREF.value)
        assertEquals("rel", AtomKeyword.LINK_REL.value)
        assertEquals("alternate", AtomKeyword.LINK_REL_ALTERNATE.value)
        assertEquals("entry", AtomKeyword.ENTRY_ITEM.value)
        assertEquals("id", AtomKeyword.ENTRY_GUID.value)
        assertEquals("content", AtomKeyword.ENTRY_CONTENT.value)
        assertEquals("published", AtomKeyword.ENTRY_PUBLISHED.value)
        assertEquals("summary", AtomKeyword.ENTRY_DESCRIPTION.value)
        assertEquals("name", AtomKeyword.ENTRY_AUTHOR.value)
        assertEquals("yt:channelId", AtomKeyword.YOUTUBE_CHANNEL_ID.value)
        assertEquals("yt:videoId", AtomKeyword.YOUTUBE_VIDEO_ID.value)
        assertEquals("media:group", AtomKeyword.YOUTUBE_MEDIA_GROUP.value)
        assertEquals("media:title", AtomKeyword.YOUTUBE_MEDIA_GROUP_TITLE.value)
        assertEquals("media:content", AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT.value)
        assertEquals("url", AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT_URL.value)
        assertEquals("media:thumbnail", AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL.value)
        assertEquals("url", AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL_URL.value)
        assertEquals("media:description", AtomKeyword.YOUTUBE_MEDIA_GROUP_DESCRIPTION.value)
        assertEquals("media:community", AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY.value)
        assertEquals("media:starRating", AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING.value)
        assertEquals("count", AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING_COUNT.value)
        assertEquals("media:statistics", AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS.value)
        assertEquals("views", AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS_VIEWS.value)
    }
}
