/*
*   Copyright 2019 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserImageEmptyTag : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Hacker Noon",
        link = "https://hackernoon.com",
        description = "How hackers start their afternoons.",
        image = null,
        lastBuildDate = "Sun, 29 Oct 2023 10:00:20 GMT",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://hackernoon.com/miscellaneous-directions?source=rss",
                title = "MISCELLANEOUS DIRECTIONS.",
                author = "Catharine Esther Beecher",
                link = "https://hackernoon.com/miscellaneous-directions?source=rss",
                pubDate = "Sun, 29 Oct 2023 09:00:01 GMT",
                description = "Every woman should know how to direct in regard to the proper care of domestic animals.",
                content = null,
                image = "https://hackernoon.com/https://cdn.hackernoon.com/images/3cu1ROR1ocaekNNTdramI0w9qNj2-xl93swo.jpeg",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("domestic-manuals"),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-test-image-empty-tag.xml")
        assertEquals(expectedChannel, channel)
    }
}
