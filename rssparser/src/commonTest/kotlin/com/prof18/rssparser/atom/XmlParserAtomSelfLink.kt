package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomSelfLink : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Simon Willison's Weblog",
        link = "http://simonwillison.net/",
        description = null,
        image = null,
        lastBuildDate = "2023-07-29T21:23:21+00:00",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "http://simonwillison.net/2023/Jul/29/a-steering-council-notice-about-pep-703/#atom-everything",
                title = "A Steering Council notice about PEP 703 (Making the Global Interpreter Lock Optional\n" +
                    "            in CPython)",
                author = null,
                link = "http://simonwillison.net/2023/Jul/29/a-steering-council-notice-about-pep-703/#atom-everything",
                pubDate = "2023-07-29T21:23:21+00:00",
                description = "<p><a\n" +
                    "            href=\"https://discuss.python.org/t/a-steering-council-notice-about-pep-703-making-the-global-interpreter-lock-optional-in-cpython/30474\">A\n" +
                    "            Steering Council notice about PEP 703 (Making the Global Interpreter Lock Optional in\n" +
                    "            CPython)</a></p> <p>Huge news concerning the nogil research fork of\n" +
                    "            Python: &quot; It’s clear that the overall sentiment is positive, both for the\n" +
                    "            general idea and for PEP 703 specifically. The Steering Council is also largely positive\n" +
                    "            on both. We intend to accept PEP 703, although we’re still working on the acceptance\n" +
                    "            details.&quot;</p>\n" +
                    "\n" +
                    "            <p>Via <a href=\"https://twitter.com/charliermarsh/status/1685068890424967168\">@charliermarsh</a></p>",
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
        val channel = parseFeed("atom-self-link-example.xml")
        assertEquals(expectedChannel, channel)
    }
}
