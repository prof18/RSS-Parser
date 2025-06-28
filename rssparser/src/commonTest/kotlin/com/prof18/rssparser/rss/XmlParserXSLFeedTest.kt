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
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserXSLFeedTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "SkySports | Liverpool",
        link = "http://www.skysports.com",
        description = "Liverpool News",
        image = RssImage(
            title = "Sky Sports",
            url = "https://www.skysports.com/images/site/ss-logo-07.gif",
            link = "https://www.skysports.com",
            description = null,
        ),
        lastBuildDate = "Fri, 17 May 2019 23:21:44 BST",
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
                title = "Insight: Who should Liverpool sign?",
                author = null,
                link = "https://www.skysports.com/football/news/11669/11719097/premier-league-transfer-window-who-should-liverpool-sign",
                pubDate = "Fri, 17 May 2019 06:00:00 BST",
                description = "Liverpool just missed out on clinching the Premier League title and have a Champions League final to look forward to - so where could they improve?",
                content = null,
                image = "https://e2.365dm.com/19/04/128x67/skysports-jurgen-klopp-liverpool_4654732.jpg?20190430113948",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("News Story"),
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
                    url = "https://e2.365dm.com/19/04/128x67/skysports-jurgen-klopp-liverpool_4654732.jpg?20190430113948",
                    length = 123456,
                    type = "image/jpg",
                ),
            ),
            RssItem(
                guid = null,
                title = "De Bruyne: No sympathy for Liverpool",
                author = null,
                link = "https://www.skysports.com/football/news/11669/11722674/man-citys-kevin-de-bruyne-has-no-sympathy-for-liverpool",
                pubDate = "Fri, 17 May 2019 22:34:00 BST",
                description = "Kevin De Bruyne feels no sympathy for Liverpool after Manchester City narrowly pipped them to the Premier League title.",
                content = null,
                image = "https://e0.365dm.com/19/04/128x67/skysports-kevin-de-bruyne-manchester-city_4643028.jpg?20190417213007",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("News Story"),
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
                    url = "https://e0.365dm.com/19/04/128x67/skysports-kevin-de-bruyne-manchester-city_4643028.jpg?20190417213007",
                    length = 123456,
                    type = "image/jpg",
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-test-xsl.xml")
        assertEquals(expectedChannel, channel)
    }
}
