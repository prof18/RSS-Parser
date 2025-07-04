/*
*   Copyright 2020 Marco Gomiero
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

class XmlParserCommentsTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Hacker News",
        link = "https://news.ycombinator.com/",
        description = "Links for the intellectually curious, ranked by readers.",
        image = null,
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = null,
                title = "GPTZero case study discovers it&#x27;s only accurate on less than 50% of text",
                author = null,
                link = "https://gonzoknows.com/posts/GPTZero-Case-Study/",
                pubDate = "Sun, 19 Feb 2023 15:22:39 +0000",
                description = "<a href=\"https://news.ycombinator.com/item?id=34858307\">Comments</a>",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = "https://news.ycombinator.com/item?id=34858307",
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-comment.xml")
        assertEquals(expectedChannel, channel)
    }
}
