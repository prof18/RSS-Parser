package com.prof18.rssparser.atom

import com.prof18.rssparser.BASE_FEED_URL
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

class XmlParserAtomRelativeLink : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Aurimas Liutikas",
        link = "$BASE_FEED_URL/",
        description = "A personal page of Aurimas Liutikas.",
        image = null,
        lastBuildDate = "2024-12-19T06:13:47+00:00",
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
                guid = "/2024/12/18/What-The-Distribution",
                title = "What The Distribution - What Is Inside Gradle Distribution Zips?",
                author = "",
                link = "$BASE_FEED_URL/2024/12/18/What-The-Distribution.html",
                pubDate = "2024-12-18T00:00:00+00:00",
                description = "When I saw this post",
                content = null,
                image = null,
                audio = null,
                video = null,
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
                    url = null,
                    length = null,
                    type = null,
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("atom-relative-url.xml")
        assertEquals(expectedChannel, channel)
    }
}
