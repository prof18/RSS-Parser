package com.prof18.rssparser.atom

import com.prof18.rssparser.CurrentTarget
import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.currentTarget
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomContentHtmlTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Jake Wharton",
        link = "https://jakewharton.com/",
        description = null,
        image = null,
        lastBuildDate = "2023-04-28T20:00:30+00:00",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://jakewharton.com/the-state-of-managing-state-with-compose",
                title = "The state of managing state (with Compose)",
                author = null,
                link = "https://code.cash.app/the-state-of-managing-state-with-compose",
                pubDate = "2021-11-11T00:00:00+00:00",
                description = null,
                content = when (currentTarget) {
                    CurrentTarget.JVM -> {
                        "https://code.cash.app/the-state-of-managing-state-with-compose."
                    }
                    CurrentTarget.APPLE -> {
                        "This post was published externally on Cash App Code Blog. Read it at"
                    }
                    CurrentTarget.WEB -> {
                        "This post was published externally on Cash App Code Blog. Read it at https://code.cash.app/the-state-of-managing-state-with-compose."
                    }
                    else -> {
                        null
                    }
                },
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = emptyList(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-test-atom-content-html.xml")
        assertEquals(expectedChannel, channel)
    }
}
