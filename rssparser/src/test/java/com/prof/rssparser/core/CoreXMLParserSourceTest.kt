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
class CoreXMLParserSourceTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-source.xml"
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
        assertEquals("À la une - Google Actualités", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("Google Actualités", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://news.google.com/?hl=fr&gl=BE&ceid=BE:fr", channel.link)
    }

    @Test
    fun channelImage_isNull() {
        assertNull(channel.image)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Sun, 08 Mar 2020 15:34:10 GMT", channel.lastBuildDate)
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
            "Coronavirus en Belgique: 200 cas détectés au total, un nombre limité de tests effectués par manque de réactifs - RTBF",
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
            "https://news.google.com/__i/rss/rd/articles/CBMiiwFodHRwczovL3d3dy5ydGJmLmJlL2luZm8vc29jaWV0ZS9kZXRhaWxfY29yb25hdmlydXMtZW4tYmVsZ2lxdWUtMjAwLWNhcy1kZXRlY3Rlcy1hdS10b3RhbC1sYS1jaXJjdWxhdGlvbi1kdS12aXJ1cy1yZXN0ZS1saW1pdGVlP2lkPTEwNDUwNzkz0gEA?oc=5",
            article.link
        )
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Sun, 08 Mar 2020 10:32:07 GMT", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "<ol><li><a href=\"https://news.google.com/__i/rss/rd/articles/CBMiiwFodHRwczovL3d3dy5ydGJmLmJlL2luZm8vc29jaWV0ZS9kZXRhaWxfY29yb25hdmlydXMtZW4tYmVsZ2lxdWUtMjAwLWNhcy1kZXRlY3Rlcy1hdS10b3RhbC1sYS1jaXJjdWxhdGlvbi1kdS12aXJ1cy1yZXN0ZS1saW1pdGVlP2lkPTEwNDUwNzkz0gEA?oc=5\" target=\"_blank\">Coronavirus en Belgique: 200 cas détectés au total, un nombre limité de tests effectués par manque de réactifs</a>&nbsp;&nbsp;<font color=\"#6f6f6f\">RTBF</font></li><li><a href=\"https://news.google.com/__i/rss/rd/articles/CBMifmh0dHBzOi8vd3d3LnN1ZGluZm8uYmUvaWQxNzE5MjcvYXJ0aWNsZS8yMDIwLTAzLTA4L2Nvcm9uYXZpcnVzLTMxLW5vdXZlbGxlcy1pbmZlY3Rpb25zLWVuLWJlbGdpcXVlLWRvbnQtNy1lbi13YWxsb25pZS1sZS10b3RhbNIBAA?oc=5\" target=\"_blank\">Coronavirus: 31 nouvelles infections en Belgique, dont 7 en Wallonie, le total porté à 200</a>&nbsp;&nbsp;<font color=\"#6f6f6f\">Sudinfo.be</font></li><li><a href=\"https://news.google.com/__i/rss/rd/articles/CBMiXWh0dHBzOi8vd3d3LmRobmV0LmJlL2FjdHUvc2FudGUvbGEtYmVsZ2lxdWUtYW5ub25jZS14eHgtbm91dmVhdXgtY2FzLTVlNjRjNzI2ZDhhZDU4MzVhMWM4ZTA3MNIBAA?oc=5\" target=\"_blank\">La Belgique annonce 31 nouveaux cas de coronavirus: un total de 200 personnes contaminées</a>&nbsp;&nbsp;<font color=\"#6f6f6f\">dh.be</font></li><li><a href=\"https://news.google.com/__i/rss/rd/articles/CBMicGh0dHBzOi8vd3d3Lmxlc29pci5iZS8yODUzODQvYXJ0aWNsZS8yMDIwLTAzLTA4L2Nvcm9uYXZpcnVzLWVuLWJlbGdpcXVlLTMxLW5vdXZlbGxlcy1pbmZlY3Rpb25zLTIwMC1jYXMtcmVjZW5zZXPSAQA?oc=5\" target=\"_blank\">Coronavirus en Belgique: 31 nouvelles infections, 200 cas recensés</a>&nbsp;&nbsp;<font color=\"#6f6f6f\">Le Soir</font></li><li><a href=\"https://news.google.com/__i/rss/rd/articles/CBMicmh0dHBzOi8vd3d3Lm5vdGVsZS5iZS9pdDE4LW1lZGlhNzY2ODItY292aWQtMTktZGV1eC1wcmVtaWVycy1jYXMtZGUtY29yb25hdmlydXMtY29uZmlybWVzLWVuLXdhbGxvbmllLXBpY2FyZGUuaHRtbNIBAA?oc=5\" target=\"_blank\">Covid-19 : deux premiers cas de coronavirus confirmés en Wallonie picarde</a>&nbsp;&nbsp;<font color=\"#6f6f6f\">Notélé</font></li><li><strong><a href=\"https://news.google.com/stories/CAAqOQgKIjNDQklTSURvSmMzUnZjbmt0TXpZd1NoTUtFUWlWdXREQ2xZQU1FU0FyMXpaNWRUeDlLQUFQAQ?oc=5\" target=\"_blank\">Voir la couverture complète sur Google Actualités</a></strong></li></ol>",
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
        assertEquals("52782335008021", article.guid)
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertNull(article.audio)
    }

    @Test
    @Throws
    fun sourceName_iCorrect() {
        assertEquals("RTBF", article.sourceName)
    }

    @Test
    @Throws
    fun sourceUrl_iCorrect() {
        assertEquals("https://www.rtbf.be", article.sourceUrl)
    }

    @Test
    @Throws
    fun video_isCorrect() {
        assertNull(article.video)
    }
}