package com.prof18.rssparser.atom

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

class XmlParserAtomYoutube : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Kotlin by JetBrains",
        link = "https://www.youtube.com/channel/UCP7uiEZIqci43m22KDl0sNw",
        description = null,
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
        youtubeChannelData = YoutubeChannelData(
            channelId = "UCP7uiEZIqci43m22KDl0sNw",
        ),
        items = listOf(
            RssItem(
                guid = "yt:video:ULBuIB9lHkc",
                title = "Multi-dollar String Interpolation in Kotlin | \$\$",
                author = "Kotlin by JetBrains",
                link = "https://www.youtube.com/watch?v=ULBuIB9lHkc",
                pubDate = "2024-12-16T15:10:21+00:00",
                description = null,
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
                    videoId = "ULBuIB9lHkc",
                    title = "Multi-dollar String Interpolation in Kotlin | \$\$",
                    videoUrl = "https://www.youtube.com/v/ULBuIB9lHkc?version=3",
                    thumbnailUrl = "https://i2.ytimg.com/vi/ULBuIB9lHkc/hqdefault.jpg",
                    description = """
Kotlin’s string interpolation has been a much-liked feature from the beginning, and in this video, we'll learn about how JetBrains is adding more flexibility to it with multi-dollar string interpolation.

                To enable it:
                Ensure you are using Kotlin 2.1.0 or later in your project
                Add -Xmulti-dollar-interpolation as a compiler argument

                For IDE support:
                Use the latest 2024.3 version of IntelliJ IDEA with K2 mode enabled (requires restart)
                Get more info here: https://kotlinlang.org/docs/whatsnew21.html#multi-dollar-string-interpolation

                #kotlin #${'$'} #coffee
                    """.trimIndent(),
                    viewsCount = 4689,
                    likesCount = 247,
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
        val channel = parseFeed("atom-feed-youtube.xml")
        assertEquals(expectedChannel, channel)
    }
}
