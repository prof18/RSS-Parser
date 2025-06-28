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

package com.prof18.rssparser.atom

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

class XmlParserAtomFeedCreatedUpdated : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "tonsky.me",
        link = "https://tonsky.me/",
        description = "Nikita Prokopov’s blog",
        image = null,
        lastBuildDate = "2023-12-22T18:46:17Z",
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
                guid = "https://tonsky.me/blog/unicode/",
                title = "The Absolute Minimum Every Software Developer Must Know About Unicode in 2023 (Still No Excuses!)",
                author = "Nikita Prokopov",
                link = "https://tonsky.me/blog/unicode/",
                pubDate = "2023-10-02T00:00:00Z",
                description = "Modern extension to classic 2003 article by Joel Spolsky",
                content = null,
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
        val channel = parseFeed("atom-feed-created-updated.xml")
        assertEquals(expectedChannel, channel)
    }
}
