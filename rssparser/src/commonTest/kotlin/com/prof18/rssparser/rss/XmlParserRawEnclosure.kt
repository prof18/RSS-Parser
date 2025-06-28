package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserRawEnclosure : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "W3Schools Home Page",
        link = "https://www.w3schools.com",
        description = "Free web building tutorials",
        image = null,
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = ItunesChannelData(
            author = null,
            categories = listOf(),
            duration = null,
            explicit = null,
            image = null,
            keywords = listOf(),
            newsFeedUrl = null,
            owner = null,
            subtitle = null,
            summary = null,
            type = null,
        ),
        youtubeChannelData = YoutubeChannelData(channelId = null),
        items = listOf(
            RssItem(
                guid = null,
                title = "RSS Tutorial",
                author = null,
                link = "https://www.w3schools.com/xml/xml_rss.asp",
                pubDate = null,
                description = "New RSS tutorial on W3Schools",
                content = null,
                image = null,
                audio = null,
                video = "https://www.w3schools.com/media/3d.wmv",
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = ItunesItemData(
                    author = null,
                    duration = null,
                    episode = null,
                    episodeType = null,
                    explicit = null,
                    image = null,
                    keywords = listOf(),
                    subtitle = null,
                    summary = null,
                    season = null,
                ),
                youtubeItemData = YoutubeItemData(
                    videoId = null,
                    title = null,
                    videoUrl = null,
                    thumbnailUrl = null,
                    description = null,
                    viewsCount = null,
                    likesCount = null,
                ),
                rawEnclosure = RawEnclosure(
                    url = "https://www.w3schools.com/media/3d.wmv",
                    length = 78645,
                    type = "video/wmv",
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-raw-enclosure.xml")
        assertEquals(expectedChannel, channel)
    }
}
