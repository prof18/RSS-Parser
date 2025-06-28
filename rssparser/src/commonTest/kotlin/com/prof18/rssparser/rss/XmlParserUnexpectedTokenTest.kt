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

class XmlParserUnexpectedTokenTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Wheels Off-Road & 4x4",
        link = "https://www.wheels24.co.za/",
        description = null,
        image = null,
        lastBuildDate = "Wed, 23 Sep 2020 20:51:07 +0200",
        updatePeriod = null,
        itunesChannelData = ItunesChannelData(
            author = null,
            categories = listOf(),
            duration = null,
            explicit = null,
            image = null,
            keywords = listOf(),
            newsFeedUrl = null,
            owner = null,
            subtitle = null,
            summary = null,
            type = null,
        ),
        youtubeChannelData = YoutubeChannelData(channelId = null),
        items = listOf(
            RssItem(
                guid = null,
                title = "Wheels24.co.za | WATCH | Range Rover spices up its Velar line-up, adds new hybrid model to the range",
                author = null,
                link = "https://www.wheels24.co.za/OffRoad_and_4x4/Bakkie_and_SUV/watch-range-rover-spices-up-its-velar-line-up-adds-new-hybrid-model-to-the-range-20200923-3",
                pubDate = "Wed, 23 Sep 2020 14:45:10 +0200",
                description = "Range Rover updated its desirable Velar range by adding new engine choices and a 640Nm hybrid model.",
                content = null,
                image = "https://scripts.24.co.za/img/sites/wheels24.png",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = ItunesItemData(
                    author = null,
                    duration = null,
                    episode = null,
                    episodeType = null,
                    explicit = null,
                    image = null,
                    keywords = listOf(),
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
                    url = "https://scripts.24.co.za/img/sites/wheels24.png",
                    length = 1,
                    type = "image/png",
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-test-unexpected-token.xml")
        assertEquals(expectedChannel, channel)
    }
}
