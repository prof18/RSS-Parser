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
class CoreXMLParserImageFeedTest {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-image.xml"
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
        assertEquals(channel.title, "Movie Reviews")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(channel.description, "Movie Reviews at MovieWeb")
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://movieweb.com/movie-reviews/")
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 50)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "The Sun Is Also a Star Review: Yara Shahidi & Charles Melton Elevate Teen Romance")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://movieweb.com/the-sun-is-also-a-star-review/")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Wed, 15 May 2019 16:52:24 PDT")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "The Sun Is Also a Star is a diverse romance that bucks Hollywood's YA genre.")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)  }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "https://cdn3.movieweb.com/i/article/ABvTB3C2AERsBFALiokUbPAwoYXIC4/1200:100/The-Sun-Is-Also-A-Star-Review.jpg")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf<String>())
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(article.guid, "https://movieweb.com/the-sun-is-also-a-star-review/")
    }
}