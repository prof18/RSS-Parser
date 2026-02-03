package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomEntryRelatedLinkTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "Visual Studio Code - Code Editing. Redefined.",
        link = "https://code.visualstudio.com/",
        description = null,
        image = null,
        lastBuildDate = "2026-02-04T17:00:00.000Z",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://code.visualstudio.com/updates/v1_109",
                title = "January 2026 Insiders (version 1.109)",
                author = null,
                link = "https://code.visualstudio.com/updates/v1_109",
                pubDate = "2026-02-04T17:00:00.000Z",
                description = null,
                content = "Learn what is new.",
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                itunesItemData = null,
                commentsUrl = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("atom-feed-entry-related-link.xml")
        assertEquals(expectedChannel, channel)
    }
}
