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
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserTimeFeedTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Drug Recalls",
        link = "http://www.fda.gov/about-fda/contact-fda/stay-informed/rss-feeds/drug-recalls/rss.xml",
        description = "",
        image = null,
        lastBuildDate = null,
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
                guid = "http://www.fda.gov/safety/recalls-market-withdrawals-safety-alerts/vivimed-life-sciences-pvt-ltd-issues-voluntary-nationwide-recall-losartan-potassium-25-mg-50-mg-and",
                title = "Vivimed Life Sciences Pvt Ltd Issues Voluntary Nationwide Recall of Losartan\n" +
                    "                Potassium 25 mg, 50 mg and 100 mg Tablets, USP Due to the Detection of Trace Amounts\n" +
                    "                of N-Nitroso-N-methyl-4-aminobutyric acid (NMBA) Impurity",
                author = "FDA",
                link = "http://www.fda.gov/safety/recalls-market-withdrawals-safety-alerts/vivimed-life-sciences-pvt-ltd-issues-voluntary-nationwide-recall-losartan-potassium-25-mg-50-mg-and",
                pubDate = "Fri, 05/03/2019 - 15:21",
                description = "Vivimed Life Sciences Pvt Ltd (Vivimed) is recalling 19 lots of Losartan\n                Potassium Tablets USP 25 mg, 50 mg, and 100 mg to consumer level. Due to the\n                detection of an impurity – N-Nitroso-N-methyl-4-aminobutyric acid (NMBA) – that is\n                above the US Food & Drug Administration’s interim acceptable exposu",
                content = null,
                image = null,
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
            This tag is not supported on Web, for now.
            <pubDate>
                <time datetime="2019-05-03T19:21:28Z">Fri, 05/03/2019 - 15:21</time>
            </pubDate>
         */
        if (currentTarget == CurrentTarget.WEB) {
            return@runTest
        }
        val channel = parseFeed("feed-test-time.xml")
        assertEquals(expectedChannel, channel)
    }
}
