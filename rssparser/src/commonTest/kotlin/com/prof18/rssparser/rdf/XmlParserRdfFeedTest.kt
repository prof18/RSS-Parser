package com.prof18.rssparser.rdf

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserRdfFeedTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "news.ORF.at",
        link = "https://orf.at/",
        description = "Die aktuellsten Nachrichten auf einen Blick - aus Österreich und der ganzen Welt. In Text, Bild und Video.",
        image = RssImage(
            url = "https://distrowatch.com/images/other/dw.png",
            title = null,
            link = null,
            description = null,
        ),
        lastBuildDate = "2025-01-03T10:14:24+01:00",
        updatePeriod = "hourly",
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = null,
                title = "Kiew erneut Ziel russischer Drohnenangriffe",
                author = "ORF Online und Teletext GmbH & Co KG",
                link = "https://orf.at/stories/3380633/",
                pubDate = "2025-01-03T10:14:24+01:00",
                description = "The DistroWatch Weekly news feed is brought to you by",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("Ausland"),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("rdf-feed-test.xml")
        assertEquals(expectedChannel, channel)
    }
}
