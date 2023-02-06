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
class CoreXMLParserImageFeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-image.xml"
    private lateinit var channel: Channel

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        channel = CoreXMLParser.parseXML(inputStream)
        articleList = channel.articles
        article = articleList[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals("Movie Reviews", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("Movie Reviews at MovieWeb", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://movieweb.com/movie-reviews/", channel.link)
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals("Movie Reviews", channel.image?.title)
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals("https://movieweb.com/movie-reviews/", channel.image?.link)
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals(
            "https://cdn.movieweb.com/assets/1/sites/movieweb.com/chrome-touch-icon-192x192.png",
            channel.image?.url
        )
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertNull(channel.image?.description)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Fri, 17 May 2019 00:24:34 PDT", channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(50, articleList.size)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals("The Sun Is Also a Star Review: Yara Shahidi & Charles Melton Elevate Teen Romance", article.title)
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(null, article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals("https://movieweb.com/the-sun-is-also-a-star-review/", article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Wed, 15 May 2019 16:52:24 PDT", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "The Sun Is Also a Star is a diverse romance that bucks Hollywood's YA genre.",
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
            "https://cdn3.movieweb.com/i/article/ABvTB3C2AERsBFALiokUbPAwoYXIC4/1200:100/The-Sun-Is-Also-A-Star-Review.jpg",
            article.image
        )
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(mutableListOf<String>(), article.categories)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals("https://movieweb.com/the-sun-is-also-a-star-review/", article.guid)
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