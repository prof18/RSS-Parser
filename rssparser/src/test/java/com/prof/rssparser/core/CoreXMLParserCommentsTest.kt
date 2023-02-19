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
class CoreXMLParserCommentsTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-comment.xml"
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
        assertEquals("Hacker News", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("Links for the intellectually curious, ranked by readers.", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://news.ycombinator.com/", channel.link)
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
        assertEquals(1, articleList.size)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(
            "GPTZero case study discovers it&#x27;s only accurate on less than 50% of text",
            article.title
        )
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
            "https://gonzoknows.com/posts/GPTZero-Case-Study/",
            article.link
        )
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Sun, 19 Feb 2023 15:22:39 +0000", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "<a href=\"https://news.ycombinator.com/item?id=34858307\">Comments</a>",
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
        assertNull(article.image)
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

    @Test
    fun comments_isCorrect() {
        assertEquals("https://news.ycombinator.com/item?id=34858307", article.commentsUrl)
    }
 }
