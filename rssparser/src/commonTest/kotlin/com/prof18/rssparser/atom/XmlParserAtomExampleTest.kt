package com.prof18.rssparser.atom

import com.prof18.rssparser.CurrentTarget
import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.currentTarget
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

class XmlParserAtomExampleTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Example Feed",
        link = "http://example.org/",
        description = "A subtitle.",
        image = null,
        lastBuildDate = "2003-12-13T18:30:02Z",
        updatePeriod = null,
        itunesChannelData = ItunesChannelData(
            author = null,
            categories = emptyList(),
            duration = null,
            explicit = null,
            image = null,
            keywords = emptyList(),
            newsFeedUrl = null,
            owner = null,
            subtitle = null,
            summary = null,
            type = null,
        ),
        youtubeChannelData = YoutubeChannelData(channelId = null),
        items = listOf(
            RssItem(
                guid = "urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a",
                title = "Atom-Powered Robots Run Amok",
                author = "John Doe",
                link = "http://example.org/2003/12/13/atom03.html",
                pubDate = "2003-12-13T18:30:02Z",
                description = "Some text.",
                content = when (currentTarget) {
                    CurrentTarget.JVM -> {
                        "This is the entry content."
                    }
                    CurrentTarget.APPLE -> {
                        ""
                    }
                    CurrentTarget.WEB -> {
                        "This is the entry content."
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
                itunesItemData = ItunesItemData(
                    author = null,
                    duration = null,
                    episode = null,
                    episodeType = null,
                    explicit = null,
                    image = null,
                    keywords = emptyList(),
                    subtitle = null,
                    summary = null,
                    season = null,
                ),
                youtubeItemData = YoutubeItemData(
                    videoId = null,
                    title = null,
                    videoUrl = null,
                    thumbnailUrl = null,
                    description = null,
                    viewsCount = null,
                    likesCount = null,
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
        val channel = parseFeed("atom-test-example.xml")
        assertEquals(expectedChannel, channel)
    }
}
