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

class XmlParserImageLinkTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "Bleacher Report - Front Page",
        link = "https://bleacherreport.com",
        description = "Bleacher Report - The latest articles about sports news, rumors, teams, events, and more.",
        image = null,
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                title = "Orange Cassidy Retains AEW International Title at Double or Nothing 2023",
                author = null,
                link = "https://bleacherreport.com/articles/10077424-orange-cassidy-retains-aew-international-title-at-double-or-nothing-2023",
                pubDate = "2023-05-29T00:34:27Z",
                description = "Orange Cassidy won a 21-man Blackjack Battle Royal at Double or Nothing on Sunday to retain the AEW International Championship. The action-packed opener ended…",
                content = null,
                image = "https://media.bleacherreport.com/image/upload/x_0,y_43,w_1800,h_1200,c_crop/v1685197298/ieh5bpyilj43f4dzpgua.jpg",
                categories = listOf("Breaking News", "All Elite Wrestling", "BNT MISC", "Orange Cassidy"),
                guid = "https://bleacherreport.com/articles/10077424-orange-cassidy-retains-aew-international-title-at-double-or-nothing-2023",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                commentsUrl = "https://bleacherreport.com/articles/10077424-orange-cassidy-retains-aew-international-title-at-double-or-nothing-2023",
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null
            )
        )
    )

    @Test
    fun testXmlParserImageLink() = runTest {
        val feedChannel = parseFeed("feed-test-image-link.xml")
        assertEquals(expectedChannel, feedChannel)
    }
}
