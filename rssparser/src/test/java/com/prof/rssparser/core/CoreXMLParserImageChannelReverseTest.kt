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
class CoreXMLParserImageChannelReverseTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-image-channel-reverse.xml"
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
        assertEquals("The Joe Rogan Experience", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("The podcast of Comedian Joe Rogan..", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://www.joerogan.com", channel.link)
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals("The Joe Rogan Experience", channel.image?.title)
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals("https://www.joerogan.com", channel.image?.link)
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals("http://static.libsyn.com/p/assets/7/1/f/3/71f3014e14ef2722/JREiTunesImage2.jpg", channel.image?.url)
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertNull(channel.image?.description)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Sat, 04 Jan 2020 01:06:48 +0000", channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(6, articleList.size)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals("#1405 - Sober October 3 Recap", article.title)
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(null, article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals("http://traffic.libsyn.com/joeroganexp/p1405.mp3", article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Tue, 24 Dec 2019 20:00:00 +0000", article.pubDate)
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertEquals("Joe is joined by Ari Shaffir, Bert Kreischer & Tom Segura to recap their 3rd annual Sober October challenge.", article.description)
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals("Joe is joined by Ari Shaffir, Bert Kreischer & Tom Segura to recap their 3rd annual Sober October challenge.", article.content)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
       assert(article.categories.isEmpty())
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals("0d7147a3-f1c1-4ae6-bbf8-2e0a493639ca", article.guid)
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertEquals("http://traffic.libsyn.com/joeroganexp/p1405.mp3?dest-id=19997", article.audio)
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
        assertEquals("yes", channel.itunesChannelData!!.explicit)
    }

    @Test
    fun itunesChannel_type_isCorrect() {
        assertEquals("episodic", channel.itunesChannelData!!.type)
    }

    @Test
    fun itunesChannel_subtitle_isCorrect() {
        assertEquals("Joe Rogan's Weekly Podcast", channel.itunesChannelData!!.subtitle)
    }

    @Test
    fun itunesChannel_author_isCorrect() {
        assertEquals("Joe Rogan", channel.itunesChannelData!!.author)
    }

    @Test
    fun itunesChannel_summary_isCorrect() {
        assertEquals("The podcast of Comedian Joe Rogan..", channel.itunesChannelData!!.summary)
    }

    @Test
    fun itunesChannel_owner_isCorrect() {
        assertEquals("Joe Rogan", channel.itunesChannelData!!.owner!!.name)
        assertEquals("joe@joerogan.net", channel.itunesChannelData!!.owner!!.email)
    }

    @Test
    fun itunesChannel_image_isCorrect() {
        assertEquals(
            "http://static.libsyn.com/p/assets/7/1/f/3/71f3014e14ef2722/JREiTunesImage2.jpg",
            channel.itunesChannelData!!.image
        )
    }

    @Test
    fun itunesChannel_category_isCorrect() {
        assertEquals(listOf("Comedy","Society & Culture", "Technology", "Podcasting"), channel.itunesChannelData!!.categories)
    }

    @Test
    fun itunesChannel_newFeedUrl_isCorrect() {
        assertEquals("http://joeroganexp.joerogan.libsynpro.com/rss", channel.itunesChannelData!!.newsFeedUrl)
    }

    @Test
    fun itunesChannel_keywords_isCorrect() {
        assertEquals(listOf("comedian","joe","monkey","redban","rogan","talking","ufc",), channel.itunesChannelData!!.keywords)
    }

    @Test
    fun itunesItem_episodeType_isCorrect() {
        assertEquals("full", article.itunesArticleData!!.episodeType)
    }

    @Test
    fun itunesItem_author_isCorrect() {
        assertNull(article.itunesArticleData!!.author)
    }

    @Test
    fun itunesItem_image_isCorrect() {
        assertEquals(
            "http://static.libsyn.com/p/assets/2/8/7/9/28797cc6f284596e/JRE1405.jpg",
            article.itunesArticleData!!.image
        )
    }

    @Test
    fun itunesItem_subtitle_isCorrect() {
        assertEquals(
            "Joe is joined by Ari Shaffir, Bert Kreischer & Tom Segura to recap their 3rd annual Sober October challenge.",
            article.itunesArticleData!!.subtitle
        )
    }

    @Test
    fun itunesItem_summary_isCorrect() {
        assertNull(article.itunesArticleData!!.summary)
    }

    @Test
    fun itunesItem_duration_isCorrect() {
        assertEquals("03:30:48", article.itunesArticleData!!.duration)
    }

    @Test
    fun itunesItem_explicit_isCorrect() {
        assertEquals("no", article.itunesArticleData!!.explicit)
    }

    @Test
    fun itunesItem_keywords_isCorrect() {
        assertEquals(listOf("podcast","3","joe","party","experience","tom","ari","october","bert","freak","rogan","recap","sober","kreischer","shaffir","segura","deathsquad","jre","1405"), article.itunesArticleData!!.keywords)
    }
}