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
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserCharsetFeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-charset.xml"
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
        assertEquals("Lørdagsrådet", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("Hver uke blar tre (mer eller mindre) kvalifiserte rådgivere i sitt livserfaringsarkiv for å hjelpe deg. Lurer du på om du skal gjøre det slutt, er naboen din en tyrann eller er du håpløst forelsket? Vi skal prøve å gi deg gode råd. <a href=\"https://radio.nrk.no/podkast/loerdagsraadet?utm_source=thirdparty&utm_medium=rss&utm_content=podcastseries%3Aloerdagsraadet\">Hør alle episodene først i appen NRK Radio</a>", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://radio.nrk.no/podkast/loerdagsraadet", channel.link)
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals("Lørdagsrådet", channel.image?.title)
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals("https://radio.nrk.no/podkast/loerdagsraadet", channel.image?.link)
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals(
            "https://gfx.nrk.no/B4Wi9oIWqfMo0PBc0Hk28AhpXzswFi4Ir3NcS4uO23PA.png",
            channel.image?.url
        )
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertNull(channel.image?.description)
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
    fun title_isCorrect() {
        assertEquals("Høydepunkter fra 2022 - nr.11", article.title)
    }

    @Test
    fun author_isCorrect() {
        assertEquals(null, article.author)
    }

    @Test
    fun link_isCorrect() {
        assertNull(article.link)
    }

    @Test
    fun pubDate_isCorrect() {
        assertEquals("Sat, 31 Dec 2022 08:00:00 GMT", article.pubDate)
    }

    @Test
    fun description_isCorrect() {
        assertEquals(
            "Høydepunkter fra 2022 - nr.11. <a href=\"https://radio.nrk.no/podkast/loerdagsraadet/l_e6338d4f-2777-43c4-b38d-4f2777e3c4de?utm_source=thirdparty&utm_medium=rss&utm_content=podcast%3Al_e6338d4f-2777-43c4-b38d-4f2777e3c4de\">Hør episoden i appen NRK Radio</a>",
            article.description
        )
    }

    @Test
    fun content_isCorrect() {
        assertEquals(null, article.content)
    }

    @Test
    fun image_isCorrect() {
        assertNull(article.image)
    }

    @Test
    fun categories_isCorrect() {
        assertEquals(mutableListOf<String>(), article.categories)
    }

    @Test
    fun guid_isCorrect() {
        assertEquals("l_e6338d4f-2777-43c4-b38d-4f2777e3c4de", article.guid)
    }

    @Test
    fun audio_iCorrect() {
        assertEquals("https://podkast.nrk.no/fil/loerdagsraadet/23ee45fc-d869-4110-bf97-34138fcfaba6_0_ID192MP3.mp3", article.audio)
    }

    @Test
    fun sourceName_iCorrect() {
        assertNull(article.sourceName)
    }

    @Test
    fun sourceUrl_iCorrect() {
        assertNull(article.sourceUrl)
    }

    @Test
    fun video_isCorrect() {
        assertNull(article.video)
    }

    @Test
    fun itunesData_iCorrect() {
        assertNotNull(article.itunesArticleData)
    }
}