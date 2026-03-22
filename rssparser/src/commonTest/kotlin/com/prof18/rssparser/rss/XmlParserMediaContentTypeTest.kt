package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RawMediaContent
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserMediaContentTypeTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "Test Media Content Types",
        link = "https://example.com",
        description = "Feed testing media:content type filtering",
        image = null,
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            // application/java-archive: must NOT be assigned to image
            RssItem(
                guid = "https://example.com/files/large-file.zip/download",
                title = "Non-image archive file",
                author = null,
                link = "https://example.com/files/large-file.zip/download",
                pubDate = null,
                description = "Item with application/java-archive media:content",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
                rawMediaContent = RawMediaContent(
                    url = "https://example.com/files/large-file.zip/download",
                    type = "application/java-archive; charset=binary",
                    medium = null,
                ),
            ),
            // image/jpeg with medium=image: assigned to image
            RssItem(
                guid = "https://example.com/item2",
                title = "Image with type and medium",
                author = null,
                link = "https://example.com/item2",
                pubDate = null,
                description = "Item with image/jpeg type and medium=image",
                content = null,
                image = "https://example.com/images/photo.jpg",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
                rawMediaContent = RawMediaContent(
                    url = "https://example.com/images/photo.jpg",
                    type = "image/jpeg",
                    medium = "image",
                ),
            ),
            // video/mp4 with medium=video: assigned to video, NOT image
            RssItem(
                guid = "https://example.com/item3",
                title = "Video with medium",
                author = null,
                link = "https://example.com/item3",
                pubDate = null,
                description = "Item with medium=video",
                content = null,
                image = null,
                audio = null,
                video = "https://example.com/videos/clip.mp4",
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
                rawMediaContent = RawMediaContent(
                    url = "https://example.com/videos/clip.mp4",
                    type = "video/mp4",
                    medium = "video",
                ),
            ),
            // audio/mpeg with medium=audio: assigned to audio
            RssItem(
                guid = "https://example.com/item4",
                title = "Audio with medium",
                author = null,
                link = "https://example.com/item4",
                pubDate = null,
                description = "Item with medium=audio",
                content = null,
                image = null,
                audio = "https://example.com/audio/track.mp3",
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
                rawMediaContent = RawMediaContent(
                    url = "https://example.com/audio/track.mp3",
                    type = "audio/mpeg",
                    medium = "audio",
                ),
            ),
            // No type or medium: NOT assigned to image (safety)
            RssItem(
                guid = "https://example.com/item5",
                title = "No type or medium",
                author = null,
                link = "https://example.com/item5",
                pubDate = null,
                description = "Item with media:content but no type or medium",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
                rawMediaContent = RawMediaContent(
                    url = "https://example.com/images/untyped.jpg",
                    type = null,
                    medium = null,
                ),
            ),
            // medium=document: must NOT be assigned to image
            RssItem(
                guid = "https://example.com/item6",
                title = "Document medium",
                author = null,
                link = "https://example.com/item6",
                pubDate = null,
                description = "Item with medium=document",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
                rawMediaContent = RawMediaContent(
                    url = "https://example.com/files/doc.pdf",
                    type = null,
                    medium = "document",
                ),
            ),
        ),
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-media-content-type.xml")
        assertEquals(expectedChannel, channel)
    }
}
