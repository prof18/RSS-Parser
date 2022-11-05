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
class CoreXMLParserImage2FeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-image-2.xml"
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
        assertEquals("F.C. Barcelona", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("F.C. Barcelona", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://www.mundodeportivo.com/futbol/fc-barcelona", channel.link)
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals("F.C. Barcelona", channel.image?.title)
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals("https://www.mundodeportivo.com/futbol/fc-barcelona", channel.image?.link)
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals("https://www.mundodeportivo.com/rsc/images/logo_MD_feed.png", channel.image?.url)
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertEquals(
            "Mundo Deportivo es tu diario deportivo On Line. Noticias de deporte, fútbol y del Barça",
            channel.image?.description
        )
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
        assertEquals(20, articleList.size)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals("Martens: “Estoy feliz y quiero seguir en el Barça”", article.title)
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
            "https://www.mundodeportivo.com/futbol/fc-barcelona/20190517/462298326260/martens-estoy-feliz-y-quiero-seguir-en-el-barca.html",
            article.link
        )
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Fri, 17 May 2019 21:33:59 +0200", article.pubDate)
    }

    @Test
    @Throws
    fun description_isPresent() {
        assert(!article.description.isNullOrEmpty())
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
            "https://www.mundodeportivo.com/ra/thumbnail/GODO/MD/p6/Barca/Imagenes/2019/05/17/Recortada/img_ppunti_20190517-210423_imagenes_md_propias_ppunti_190517fcbfem294_4_6_2298222649-kYlG-U4622983262609eF-980x554@MundoDeportivo-Web.jpg",
            article.image
        )
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(mutableListOf("F.C. Barcelona"), article.categories)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals("f5c42a9c-78d9-11e9-a24c-645e8f5d185b", article.guid)
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