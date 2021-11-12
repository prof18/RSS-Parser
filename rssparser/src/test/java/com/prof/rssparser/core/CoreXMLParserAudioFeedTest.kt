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
import com.prof.rssparser.Image
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserAudioFeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-audio.xml"
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
        assertEquals(channel.title, "Stuff You Should Know")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(
            channel.description,
            "If you've ever wanted to know about champagne, satanism, the Stonewall Uprising, chaos theory, LSD, El Nino, true crime and Rosa Parks, then look no further. Josh and Chuck have you covered."
        )
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://www.howstuffworks.com")
    }

    @Test
    fun channelImage_isNull() {
        assertEquals(
            channel.image,
            Image(
                title = "Stuff You Should Know",
                url = "https://megaphone-prod.s3.amazonaws.com/podcasts/1e705dd4-2de6-11e8-b55d-9ba6ddb3f75e/image/uploads_2F1546996139536-0o3pw93d8mk-d5f1143c14a746754c55efb478c66988_2FSKSKLogo-FINAL-iHR-3000x3000.png",
                link = "https://www.howstuffworks.com",
                description = null
            )
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
        assertEquals(articleList.size, 1)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "SYSK Selects: How Gold Works")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertNull(article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Sat, 07 Mar 2020 10:00:00 -0000")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            article.description,
            "As of early 2013, only 161,00 metric tons of gold had been mined in the entire history of the world. Considering about 85 percent of it is recycled, there's a decent chance your jewelry may once have been part of an Incan headdress or Mycenaean face mask. Dive in to gold in this classic episode.\n" +
                    "                Learn more about your ad-choices at https://news.iheart.com/podcast-advertisers"
        )
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(
            article.content,
            "<p>As of early 2013, only 161,00 metric tons of gold had been mined in the entire history of the world. Considering about 85 percent of it is recycled, there's a decent chance your jewelry may once have been part of an Incan headdress or Mycenaean face mask. Dive in to gold in this classic episode.</p><p> </p> Learn more about your ad-choices at <a href=\"https://news.iheart.com/podcast-advertisers\">https://news.iheart.com/podcast-advertisers</a>"
        )
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories.size, 0)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(article.guid, "c41042ae-5460-11e8-b38c-5f5faf9dd0c5")
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertEquals(
            article.audio,
            "https://www.podtrac.com/pts/redirect.mp3/chtbl.com/track/5899E/traffic.megaphone.fm/HSW3157672398.mp3"
        )
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
    fun itunesChannel_explicit_isCorrect() {
        assertEquals("no", channel.itunesChannelData!!.explicit)
    }

    @Test
    fun itunesChannel_type_isCorrect() {
        assertEquals("episodic", channel.itunesChannelData!!.type)
    }

    @Test
    fun itunesChannel_subtitle_isCorrect() {
        assertEquals("Stuff You Should Know", channel.itunesChannelData!!.subtitle)
    }

    @Test
    fun itunesChannel_author_isCorrect() {
        assertEquals("iHeartRadio", channel.itunesChannelData!!.author)
    }

    @Test
    fun itunesChannel_summary_isCorrect() {
        assertEquals(
            "If you've ever wanted to know about champagne, satanism, the Stonewall Uprising, chaos theory, LSD, El Nino, true crime and Rosa Parks, then look no further. Josh and Chuck have you covered.",
            channel.itunesChannelData!!.summary
        )
    }

    @Test
    fun itunesChannel_owner_isCorrect() {
        assertEquals("iHeartRadio", channel.itunesChannelData!!.owner!!.name)
        assertEquals("applepodcast@howstuffworks.com", channel.itunesChannelData!!.owner!!.email)
    }

    @Test
    fun itunesChannel_image_isCorrect() {
        assertEquals(
            "https://megaphone-prod.s3.amazonaws.com/podcasts/1e705dd4-2de6-11e8-b55d-9ba6ddb3f75e/image/uploads_2F1546996139536-0o3pw93d8mk-d5f1143c14a746754c55efb478c66988_2FSKSKLogo-FINAL-iHR-3000x3000.png",
            channel.itunesChannelData!!.image
        )
    }

    @Test
    fun itunesChannel_category_isCorrect() {
        assertEquals("Society & Culture", channel.itunesChannelData!!.category.first())
    }

    @Test
    fun itunesChannel_newFeedUrl_isCorrect() {
        assertEquals(
            "https://feeds.megaphone.fm/stuffyoushouldknow",
            channel.itunesChannelData!!.newsFeedUrl
        )
    }

    @Test
    fun itunesChannel_keywords_isCorrect() {
        assertTrue(channel.itunesChannelData!!.keywords.isEmpty())
    }

    @Test
    fun itunesItem_episodeType_isCorrect() {
        assertEquals("full", article.itunesArticleData!!.episodeType)
    }

    @Test
    fun itunesItem_author_isCorrect() {
        assertEquals("iHeartRadio", article.itunesArticleData!!.author)
    }

    @Test
    fun itunesItem_image_isCorrect() {
        assertEquals(
            "http://megaphone-prod.s3.amazonaws.com/podcasts/c41042ae-5460-11e8-b38c-5f5faf9dd0c5/image/uploads_2F1583260134090-ua5pbey0yx-25644ab8bbd946b90180c8601dbe70c1_2FSKSKLogo-FINAL-iHR-3000x3000.png",
            article.itunesArticleData!!.image
        )
    }

    @Test
    fun itunesItem_subtitle_isCorrect() {
        assertEquals(
            "As of early 2013, only 161,00 metric tons of gold had been mined in the entire history of the world. Considering about 85 percent of it is recycled, decent chance your jewelry may once have been part of an Incan headdress or Mycenaean face mask. Dive in to gold in this classic episode.",
            article.itunesArticleData!!.subtitle
        )
    }

    @Test
    fun itunesItem_summary_isCorrect() {
        assertEquals(
            "As of early 2013, only 161,00 metric tons of gold had been mined in the entire history of the world. Considering about 85 percent of it is recycled, there's a decent chance your jewelry may once have been part of an Incan headdress or Mycenaean face mask. Dive in to gold in this classic episode.\n" +
                    "                Learn more about your ad-choices at https://news.iheart.com/podcast-advertisers",
            article.itunesArticleData!!.summary
        )
    }

    @Test
    fun itunesItem_duration_isCorrect() {
        assertEquals("3122", article.itunesArticleData!!.duration)
    }
}