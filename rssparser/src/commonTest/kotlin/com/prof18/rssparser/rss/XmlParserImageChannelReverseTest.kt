package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.ItunesOwner
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserImageChannelReverseTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "The Joe Rogan Experience",
        link = "https://www.joerogan.com",
        description = "The podcast of Comedian Joe Rogan..",
        image = RssImage(
            title = "The Joe Rogan Experience",
            url = "http://static.libsyn.com/p/assets/7/1/f/3/71f3014e14ef2722/JREiTunesImage2.jpg",
            link = "https://www.joerogan.com",
            description = null
        ),
        lastBuildDate = "Sat, 04 Jan 2020 01:06:48 +0000",
        updatePeriod = null,
        items = listOf(
            RssItem(
                guid = "0d7147a3-f1c1-4ae6-bbf8-2e0a493639ca",
                title = "#1405 - Sober October 3 Recap",
                author = null,
                link = "http://traffic.libsyn.com/joeroganexp/p1405.mp3",
                pubDate = "Tue, 24 Dec 2019 20:00:00 +0000",
                description = "Joe is joined by\u00A0Ari Shaffir, Bert Kreischer & Tom Segura\u00A0to recap their 3rd annual Sober October challenge.",
                content = "Joe is joined by\u00A0Ari Shaffir, Bert Kreischer & Tom Segura\u00A0to recap their 3rd annual Sober October challenge.",
                image = null,
                audio = "http://traffic.libsyn.com/joeroganexp/p1405.mp3?dest-id=19997",
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                itunesItemData = ItunesItemData(
                    author = null,
                    duration = "03:30:48",
                    episode = "1405",
                    episodeType = "full",
                    explicit = "no",
                    image = "http://static.libsyn.com/p/assets/2/8/7/9/28797cc6f284596e/JRE1405.jpg",
                    keywords = listOf(
                        "podcast", "3", "joe", "party", "experience", "tom", "ari",
                        "october", "bert", "freak", "rogan", "recap", "sober",
                        "kreischer", "shaffir", "segura", "deathsquad", "jre", "1405"
                    ),
                    subtitle = "Joe is joined by\u00A0Ari Shaffir, Bert Kreischer & Tom Segura\u00A0to recap their 3rd annual Sober October challenge.",
                    summary = null,
                    season = null,
                ),
                commentsUrl = null,
                youtubeItemData = null,
                rawEnclosure = RawEnclosure(
                    url = "http://traffic.libsyn.com/joeroganexp/p1405.mp3?dest-id=19997",
                    length = 202715961,
                    type = "audio/mpeg",
                )
            )
        ),
        itunesChannelData = ItunesChannelData(
            author = "Joe Rogan",
            categories = listOf("Comedy", "Society & Culture", "Technology", "Podcasting"),
            duration = null,
            explicit = "yes",
            image = "http://static.libsyn.com/p/assets/7/1/f/3/71f3014e14ef2722/JREiTunesImage2.jpg",
            keywords = listOf("comedian", "joe", "monkey", "redban", "rogan", "talking", "ufc"),
            newsFeedUrl = "http://joeroganexp.joerogan.libsynpro.com/rss",
            owner = ItunesOwner(
                name = "Joe Rogan",
                email = "joe@joerogan.net"
            ),
            subtitle = "Joe Rogan's Weekly Podcast",
            summary = "The podcast of Comedian Joe Rogan..",
            type = "episodic"
        ),
        youtubeChannelData = null,
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-test-image-channel-reverse.xml")
        assertEquals(expectedChannel, channel)
    }
}
