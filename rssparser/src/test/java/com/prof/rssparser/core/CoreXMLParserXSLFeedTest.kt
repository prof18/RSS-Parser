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
class CoreXMLParserXSLFeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-xsl.xml"
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
        assertEquals("SkySports | Liverpool", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("Liverpool News", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("http://www.skysports.com", channel.link)
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals("Sky Sports", channel.image?.title)
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals("https://www.skysports.com", channel.image?.link)
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals("https://www.skysports.com/images/site/ss-logo-07.gif", channel.image?.url)
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertNull(channel.image?.description)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Fri, 17 May 2019 23:21:44 BST", channel.lastBuildDate)
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
        assertEquals("Insight: Who should Liverpool sign?", article.title)
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(null, article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(
            "https://www.skysports.com/football/news/11669/11719097/premier-league-transfer-window-who-should-liverpool-sign",
            article.link
        )
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Fri, 17 May 2019 06:00:00 BST", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "Liverpool just missed out on clinching the Premier League title and have a Champions League final to look forward to - so where could they improve?",
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
        assertEquals(
            "https://e2.365dm.com/19/04/128x67/skysports-jurgen-klopp-liverpool_4654732.jpg?20190430113948",
            article.image
        )
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(mutableListOf("News Story"), article.categories)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(null, article.guid)
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