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
class CoreXMLParserUnexpectedTokenTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-unexpected-token.xml"
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
        assertEquals("Wheels Off-Road & 4x4", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertNull(channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://www.wheels24.co.za/", channel.link)
    }

    @Test
    fun channelImage_isCorrect() {
        assertNull(channel.image)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Wed, 23 Sep 2020 20:51:07 +0200", channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(20, articleList.size)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(
            "Wheels24.co.za | WATCH | Range Rover spices up its Velar line-up, adds new hybrid model to the range",
            article.title
        )
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertNull(article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(
            "https://www.wheels24.co.za/OffRoad_and_4x4/Bakkie_and_SUV/watch-range-rover-spices-up-its-velar-line-up-adds-new-hybrid-model-to-the-range-20200923-3",
            article.link
        )
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Wed, 23 Sep 2020 14:45:10 +0200", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "Range Rover updated its desirable Velar range by adding new engine choices and a 640Nm hybrid model.",
            article.description
        )
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertNull(article.content)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals("https://scripts.24.co.za/img/sites/wheels24.png", article.image!!)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(0, article.categories.size)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertNull(article.guid)
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