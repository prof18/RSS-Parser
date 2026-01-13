package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.ItunesOwner
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserItunesImageFallbackTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Swift by Sundell",
        link = "https://www.swiftbysundell.com/podcast",
        description = "In-depth conversations about Swift and software development in general, hosted by John Sundell.",
        image = RssImage(
            title = null,
            url = "https://www.swiftbysundell.com/images/podcastArtwork.png",
            link = null,
            description = null
        ),
        lastBuildDate = "Mon, 2 Jan 2023 11:37:11 +0100",
        updatePeriod = null,
        itunesChannelData = ItunesChannelData(
            author = "John Sundell",
            categories = listOf("Technology"),
            duration = null,
            explicit = "no",
            image = "https://www.swiftbysundell.com/images/podcastArtwork.png",
            keywords = emptyList(),
            newsFeedUrl = "https://swiftbysundell.com/podcast/feed.rss",
            owner = ItunesOwner(
                name = "John Sundell",
                email = "john@swiftbysundell.com",
            ),
            subtitle = "Answering your questions about Swift development and beyond.",
            summary = "In-depth conversations about Swift and software development in general, hosted by John Sundell.",
            type = "episodic",
        ),
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://www.swiftbysundell.com/podcast/123",
                title = "123: \"The evolution of Swift\", with special guest Nick Lockwood",
                author = null,
                link = "https://www.swiftbysundell.com/podcast/123",
                pubDate = "Mon, 19 Dec 2022 16:05:00 +0100",
                description = "On this final episode of 2022, Nick Lockwood returns to the show to discuss the overall evolution of Swift and its ecosystem of tools and libraries.",
                content = null,
                image = "https://www.swiftbysundell.com/images/podcastArtwork.png",
                audio = "https://traffic.libsyn.com/secure/swiftbysundell/Episode_123.mp3",
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = ItunesItemData(
                    author = "John Sundell",
                    duration = "01:23:45",
                    episode = "123",
                    episodeType = "full",
                    explicit = "no",
                    image = "https://www.swiftbysundell.com/images/podcastArtwork.png",
                    keywords = emptyList(),
                    subtitle = "The evolution of Swift",
                    summary = "On this final episode of 2022, Nick Lockwood returns to the show to discuss the overall evolution of Swift and its ecosystem of tools and libraries.",
                    season = "1",
                ),
                youtubeItemData = null,
                rawEnclosure = RawEnclosure(
                    url = "https://traffic.libsyn.com/secure/swiftbysundell/Episode_123.mp3",
                    length = 120456789,
                    type = "audio/mpeg",
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectlyWithItunesImageFallback() = runTest {
        val channel = parseFeed("feed-itunes-image-fallback.xml")
        assertEquals(expectedChannel, channel)
    }
}
