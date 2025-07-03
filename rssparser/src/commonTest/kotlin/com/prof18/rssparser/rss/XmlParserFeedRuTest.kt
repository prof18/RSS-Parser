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
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserFeedRuTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "Аргументы и Факты",
        link = "http://www.aif.ru/",
        description = "Аргументы и Факты: объясняем, что происходит",
        image = null,
        lastBuildDate = "2019-05-18 10:58:46 +0300",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                title = "СМИ: собака в Таиланде спасла заживо похороненного младенца",
                author = null,
                link = "http://www.aif.ru/incidents/smi_sobaka_v_tailande_spasla_zazhivo_pohoronennogo_mladenca",
                pubDate = "Sat, 18 May 2019 10:52:50 +0300",
                description = "15-летняя мать ребенка решила избавиться от него, побоявшись гнева родителей",
                content = null,
                image = "https://images.aif.ru/017/020/025bc5cb4cc5d0f8347bbb27f5e4d13b.jpg",
                categories = listOf("Происшествия"),
                guid = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = RawEnclosure(
                    url = "https://images.aif.ru/017/020/025bc5cb4cc5d0f8347bbb27f5e4d13b.jpg",
                    length = 30000,
                    type = "image/jpeg",
                ),
            )
        )
    )

    @Test
    fun testXmlParserFeedRu() = runTest {
        val feedChannel = parseFeed("feed-test-ru.xml")
        assertEquals(expectedChannel, feedChannel)
    }
}
