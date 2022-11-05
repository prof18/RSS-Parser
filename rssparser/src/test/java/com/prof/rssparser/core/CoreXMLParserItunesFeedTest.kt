package com.prof.rssparser.core

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.Image
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserItunesFeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-itunes.xml"
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
        assertEquals("Conduit to the Gaian Mind", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://www.joerogan.com", channel.link)
    }

    @Test
    fun channelImage_isNull() {
        assertEquals(
            Image(
                title = "The Joe Rogan Experience",
                url = "http://static.libsyn.com/p/assets/7/1/f/3/71f3014e14ef2722/JREiTunesImage2.jpg",
                link = "https://www.joerogan.com",
                description = null
            ),
            channel.image,
        )
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Thu, 29 Jul 2021 05:45:54 +0000", channel.lastBuildDate)
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
        assertEquals("#1109 - Matthew Walker", article.title)
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertNull(article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals("http://traffic.libsyn.com/joeroganexp/p1109.mp3", article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Wed, 25 Apr 2018 22:37:10 +0000", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "Matthew Walker is Professor of Neuroscience and Psychology at the University of California, Berkeley, and Founder and Director of the Center for Human Sleep Science. Check out his book \"\" on Amazon.",
            article.description,
        )
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(
            "Matthew Walker is Professor of Neuroscience and Psychology at the University of California, Berkeley, and Founder and Director of the Center for Human Sleep Science. Check out his book \"\" on Amazon.",
            article.content,
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
        assertEquals("00a5d989b6b2cd8267cf8239f3b5585c", article.guid)
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertEquals("http://traffic.libsyn.com/joeroganexp/p1109.mp3?dest-id=19997", article.audio)
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
        assertEquals(
            "Conduit to the Gaian Mind",
            channel.itunesChannelData!!.summary
        )
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
        assertEquals(listOf("Comedy", "Society & Culture", "Technology"), channel.itunesChannelData!!.categories)
    }

    @Test
    fun itunesChannel_newFeedUrl_isCorrect() {
        assertEquals(
            "https://joeroganexp.libsyn.com/rss",
            channel.itunesChannelData!!.newsFeedUrl
        )
    }

    @Test
    fun itunesChannel_keywords_isCorrect() {
        assertEquals(
            listOf("Talking", "comedian", "joe", "monkey", "redban", "rogan", "ufc"),
            channel.itunesChannelData!!.keywords
        )
    }

    @Test
    fun itunesItem_episodeType_isCorrect() {
        assertEquals("full", article.itunesArticleData!!.episodeType)
    }

    @Test
    fun itunesItem_author_isCorrect() {
        assertEquals("Joe Rogan", article.itunesArticleData!!.author)
    }

    @Test
    fun itunesItem_image_isCorrect() {
        assertEquals(
            "http://static.libsyn.com/p/assets/6/f/b/6/6fb68f57fbe00fb1/JRE1109.jpg",
            article.itunesArticleData!!.image
        )
    }

    @Test
    fun itunesItem_subtitle_isCorrect() {
        assertEquals("#1109 - Matthew Walker", article.itunesArticleData!!.subtitle)
    }

    @Test
    fun itunesItem_summary_isCorrect() {
        assertNull(article.itunesArticleData!!.summary)
    }

    @Test
    fun itunesItem_duration_isCorrect() {
        assertEquals("02:02:35", article.itunesArticleData!!.duration)
    }

    @Test
    fun itunesItem_season_isCorrect() {
        assertNull( article.itunesArticleData!!.season)
    }

    @Test
    fun itunesItem_keywords_isCorrect() {
        assertEquals(
            listOf(
                "podcast",
                "joe",
                "party",
                "Experience",
                "walker",
                "matthew",
                "freak",
                "rogan",
                "deathsquad",
                "jre",
                "1109"
            ), article.itunesArticleData!!.keywords
        )
    }
}