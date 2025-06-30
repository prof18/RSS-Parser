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

import com.prof18.rssparser.CurrentTarget
import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.currentTarget
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

class XmlParserItemChannelImageTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "www.espn.com - TOP",
        link = "https://www.espn.com",
        description = "Latest TOP news from www.espn.com",
        image = RssImage(
            title = "www.espn.com - TOP",
            url = "https://a.espncdn.com/i/espn/teamlogos/lrg/trans/espn_dotcom_black.gif",
            link = "https://www.espn.com",
            description = null,
        ),
        lastBuildDate = "Fri, 7 May 2021 18:43:18 GMT",
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
                guid = "31393791",
                title = "Inside the mysterious world of missing sports memorabilia",
                author = null,
                link = "https://www.espn.com/mlb/story/_/id/31393791/inside-mysterious-world-missing-sports-memorabilia",
                pubDate = "Fri, 7 May 2021 10:44:02 EST",
                description = "Some of the most treasured pieces of sports memorabilia are missing, can't be authenticated or... currently reside on the moon. A look at those mysterious historic items -- and what they'd be worth in a red-hot sports memorabilia market.",
                content = null,
                image = "https://a.espncdn.com/photo/2021/0506/r850492_1296x1296_1-1.jpg",
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
                    url = null,
                    length = null,
                    type = null,
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        /*
            The following structure is not supported on WEB
            <image>
                <![CDATA[https://a.espncdn.com/photo/2021/0506/r850492_1296x1296_1-1.jpg]]>
            </image>
         */
        if (currentTarget == CurrentTarget.WEB) {
            return@runTest
        }
        val channel = parseFeed("feed-item-channel-image.xml")
        assertEquals(expectedChannel, channel)
    }
}
