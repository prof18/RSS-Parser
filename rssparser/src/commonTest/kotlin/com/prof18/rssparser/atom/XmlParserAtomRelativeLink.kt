package com.prof18.rssparser.atom

import com.prof18.rssparser.BASE_FEED_URL
import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
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
        itunesChannelData = null,
        youtubeChannelData = null,
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
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("atom-relative-url.xml")
        assertEquals(expectedChannel, channel)
    }
}
