package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserCharEscape : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "NYT > Health",
        link = "https://www.nytimes.com/section/health",
        description = "",
        image = RssImage(
            title = "NYT > Health",
            url = "https://static01.nyt.com/images/misc/NYT_logo_rss_250x40.png",
            link = "https://www.nytimes.com/section/health",
            description = null
        ),
        lastBuildDate = "Mon, 14 Jun 2021 15:26:42 +0000",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://www.nytimes.com/2021/06/11/science/drought-las-vegas-grass-mars.html",
                title = "Where the Grass is Greener, Except When It\u2019s \u2018Nonfunctional Turf\u2019",
                author = "Alan Burdick",
                link = "https://www.nytimes.com/2021/06/11/science/drought-las-vegas-grass-mars.html",
                pubDate = "Sun, 13 Jun 2021 16:21:42 +0000",
                description = "Plus, mammoths in Vegas, watermelon snow, Miami\u2019s looming sea wall and more in the Friday edition of the Science Times newsletter.",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(
                    "Conservation of Resources",
                    "Grass",
                    "Water",
                    "Deserts",
                    "Shortages",
                    "Mars (Planet)",
                    "Colorado River",
                    "Hoover Dam",
                    "Lake Mead",
                    "Las Vegas (Nev)",
                    "Mojave Desert (Calif)",
                    "Western States (US)",
                    "your-feed-science"
                ),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-char-escape.xml")
        assertEquals(expectedChannel, channel)
    }
}
