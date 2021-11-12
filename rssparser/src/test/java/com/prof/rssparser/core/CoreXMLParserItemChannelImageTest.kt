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
class CoreXMLParserItemChannelImageTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-item-channel-image.xml"
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
        assertEquals("www.espn.com - TOP", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("Latest TOP news from www.espn.com", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://www.espn.com", channel.link)
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals("www.espn.com - TOP", channel.image?.title)
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals("https://www.espn.com", channel.image?.link)
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals("https://a.espncdn.com/i/espn/teamlogos/lrg/trans/espn_dotcom_black.gif", channel.image?.url)
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertNull(channel.image?.description)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Fri, 7 May 2021 18:43:18 GMT", channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(1, articleList.size)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals("Inside the mysterious world of missing sports memorabilia", article.title)
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(null, article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals("https://www.espn.com/mlb/story/_/id/31393791/inside-mysterious-world-missing-sports-memorabilia", article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Fri, 7 May 2021 10:44:02 EST", article.pubDate)
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertEquals("Some of the most treasured pieces of sports memorabilia are missing, can't be authenticated or... currently reside on the moon. A look at those mysterious historic items -- and what they'd be worth in a red-hot sports memorabilia market.", article.description)
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(null, article.content)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals("https://a.espncdn.com/photo/2021/0506/r850492_1296x1296_1-1.jpg", article.image)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(listOf<String>(), article.categories)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals("31393791", article.guid)
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