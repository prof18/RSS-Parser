package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserMismatchedHtmlTagsTest : XmlParserTestExecutor() {

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-mismatched-html-tags.xml")

        // Both parsers now handle malformed HTML correctly and capture all text content
        val expectedDescription = "L'Ordine dei Giganti: è arrivato il DLC di Indy - Keeper, ultimo giro di presentazioni per l'avventura della Double Fine - Dispatch è quasi pronto, The Wolf Among Us 2 è assai lontano dall'esserlo - Ron Gilbert si concentra su Death By Scrolling, pausa dalle avventure grafiche"

        val expectedChannel = RssChannel(
            title = "Lucasdelirium",
            link = "https://www.lucasdelirium.it/",
            description = "Il fansite italiano dedicato alla LucasArts e ai suoi ex (Double Fine, Telltale)",
            image = null,
            lastBuildDate = null,
            updatePeriod = null,
            itunesChannelData = null,
            youtubeChannelData = null,
            items = listOf(
                RssItem(
                    guid = null,
                    title = "AGGIORNAMENTO DEL 28-9-2025",
                    author = null,
                    link = "https://www.lucasdelirium.it/",
                    pubDate = "Sun, 28 Sep 2025 12:00:00 GMT",
                    description = expectedDescription,
                    content = null,
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

        assertEquals(expectedChannel, channel)
    }
}
