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

package com.prof18.rssparser

import android.os.Build
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CoreXMLParserXSLFeedTest {
    private lateinit var articleList: MutableList<Article>
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
        assertEquals(channel.title, "SkySports | Liverpool")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(channel.description, "Liverpool News")
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "http://www.skysports.com")
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 20)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "Insight: Who should Liverpool sign?")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://www.skysports.com/football/news/11669/11719097/premier-league-transfer-window-who-should-liverpool-sign")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Fri, 17 May 2019 06:00:00 BST")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "Liverpool just missed out on clinching the Premier League title and have a Champions League final to look forward to - so where could they improve?")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "https://e2.365dm.com/19/04/128x67/skysports-jurgen-klopp-liverpool_4654732.jpg?20190430113948")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf(
                "News Story"
        ))
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(article.guid, null)
    }
}