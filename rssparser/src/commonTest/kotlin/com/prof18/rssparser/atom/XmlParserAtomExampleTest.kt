package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomExampleTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Example Feed",
        link = "http://example.org/",
        description = "A subtitle.",
        image = null,
        lastBuildDate = "2003-12-13T18:30:02Z",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a",
                title = "Atom-Powered Robots Run Amok",
                author = "John Doe",
                link = "http://example.org/2003/12/13/atom03.html",
                pubDate = "2003-12-13T18:30:02Z",
                description = "Some text.",
                content = "This is the entry content.",
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
        val channel = parseFeed("atom-test-example.xml")
        assertEquals(expectedChannel, channel)
    }
}
