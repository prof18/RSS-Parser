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

package com.prof.rssparser.core

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserTimeFeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-time.xml"
    private lateinit var channel: Channel

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        val feed = inputStream.bufferedReader().use { it.readText() }
        channel = CoreXMLParser.parseXML(feed)
        articleList = channel.articles
        article = articleList[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals("Drug Recalls", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(
            "http://www.fda.gov/about-fda/contact-fda/stay-informed/rss-feeds/drug-recalls/rss.xml",
            channel.link
        )
    }

    @Test
    fun channelImage_isNull() {
        assertNull(channel.image)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertNull(channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 20)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(
            "Vivimed Life Sciences Pvt Ltd Issues Voluntary Nationwide Recall of Losartan\n" +
                    "                Potassium 25 mg, 50 mg and 100 mg Tablets, USP Due to the Detection of Trace Amounts\n" +
                    "                of N-Nitroso-N-methyl-4-aminobutyric acid (NMBA) Impurity",
            article.title
        )
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals("FDA", article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(
            "http://www.fda.gov/safety/recalls-market-withdrawals-safety-alerts/vivimed-life-sciences-pvt-ltd-issues-voluntary-nationwide-recall-losartan-potassium-25-mg-50-mg-and",
            article.link
        )
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Fri, 05/03/2019 - 15:21", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "Vivimed Life Sciences Pvt Ltd (Vivimed) is recalling 19 lots of Losartan\n" +
                    "                Potassium Tablets USP 25 mg, 50 mg, and 100 mg to consumer level. Due to the\n" +
                    "                detection of an impurity – N-Nitroso-N-methyl-4-aminobutyric acid (NMBA) – that is\n" +
                    "                above the US Food & Drug Administration’s interim acceptable exposu",
            article.description
        )
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(null, article.content)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(null, article.image)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(mutableListOf<String>(), article.categories)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(
            "http://www.fda.gov/safety/recalls-market-withdrawals-safety-alerts/vivimed-life-sciences-pvt-ltd-issues-voluntary-nationwide-recall-losartan-potassium-25-mg-50-mg-and",
            article.guid
        )
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertNull(article.audio)
    }

    @Test
    @Throws
    fun sourceName_iCorrect() {
        assertNull(article.sourceName)
    }

    @Test
    @Throws
    fun sourceUrl_iCorrect() {
        assertNull(article.sourceUrl)
    }

    @Test
    @Throws
    fun video_isCorrect() {
        assertNull(article.video)
    }
}