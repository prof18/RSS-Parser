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
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserImageFeedTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "Movie Reviews",
        link = "https://movieweb.com/movie-reviews/",
        description = "Movie Reviews at MovieWeb",
        image = RssImage(
            title = "Movie Reviews",
            url = "https://cdn.movieweb.com/assets/1/sites/movieweb.com/chrome-touch-icon-192x192.png",
            link = "https://movieweb.com/movie-reviews/",
            description = null,
        ),
        lastBuildDate = "Fri, 17 May 2019 00:24:34 PDT",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                title = "The Sun Is Also a Star Review: Yara Shahidi & Charles Melton Elevate Teen Romance",
                author = null,
                link = "https://movieweb.com/the-sun-is-also-a-star-review/",
                pubDate = "Wed, 15 May 2019 16:52:24 PDT",
                description = "The Sun Is Also a Star is a diverse romance that bucks Hollywood's YA genre.",
                content = null,
                image = "https://cdn3.movieweb.com/i/article/ABvTB3C2AERsBFALiokUbPAwoYXIC4/1200:100/The-Sun-Is-Also-A-Star-Review.jpg",
                categories = listOf(),
                guid = "https://movieweb.com/the-sun-is-also-a-star-review/",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = RawEnclosure(
                    url = "https://cdn3.movieweb.com/i/article/ABvTB3C2AERsBFALiokUbPAwoYXIC4/1200:100/The-Sun-Is-Also-A-Star-Review.jpg",
                    length = null,
                    type = "image/jpeg",
                ),
            )
        )
    )

    @Test
    fun testXmlParserImageFeed() = runTest {
        val feedChannel = parseFeed("feed-test-image.xml")
        assertEquals(expectedChannel, feedChannel)
    }
}
