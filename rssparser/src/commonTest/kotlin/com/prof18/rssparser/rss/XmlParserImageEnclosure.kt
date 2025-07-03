package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserImageEnclosure : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Centrum dopravního výzkumu, v. v. i. (RSS 2.0)",
        link = "https://www.cdv.cz/",
        description = "Informace o novinkách na webu Centra dopravního výzkumu, pořádaných akcích v oblasti dopravy a odborných článcích z oblasti dopravy.",
        image = RssImage(
            title = "Logo Centrum dopravního výzkumu, v. v. i.",
            url = "https://www.cdv.cz/",
            link = "https://www.cdv.cz/image/logo-centrum-dopravniho-vyzkumu/",
            description = null,
        ),
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = null,
                title = "Podpora udržitelné aktivní mobility a nástroje práce s veřejností",
                author = null,
                link = "https://www.cdv.cz/podpora-udrzitelne-aktivni-mobility-a-nastroje-prace-s-verejnosti/",
                pubDate = "22.11.2022 12:16:00",
                description = null,
                content = null,
                image = "https://cdv.cz/image/rss-placeholder-image/",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("event"),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-image-enclosure.xml")
        assertEquals(expectedChannel, channel)
    }
}
