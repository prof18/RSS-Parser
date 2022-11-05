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
class CoreXMLParserCharEscape {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-char-escape.xml"
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
        assertEquals("NYT > Health", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://www.nytimes.com/section/health", channel.link)
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals("NYT > Health", channel.image?.title)
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals("https://www.nytimes.com/section/health", channel.image?.link)
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals("https://static01.nyt.com/images/misc/NYT_logo_rss_250x40.png", channel.image?.url)
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertNull(channel.image?.description)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals("Mon, 14 Jun 2021 15:26:42 +0000", channel.lastBuildDate)
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
        assertEquals("Where the Grass is Greener, Except When It’s ‘Nonfunctional Turf’", article.title)
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals("Alan Burdick", article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals("https://www.nytimes.com/2021/06/11/science/drought-las-vegas-grass-mars.html", article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Sun, 13 Jun 2021 16:21:42 +0000", article.pubDate)
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertEquals(
            "Plus, mammoths in Vegas, watermelon snow, Miami’s looming sea wall and more in the Friday edition of the Science Times newsletter.",
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
        assertEquals(
            listOf(
                "Conservation of Resources",
                "Grass",
                "Water",
                "Deserts",
                "Shortages",
                "Mars (Planet)",
                "Colorado River",
                "Hoover Dam",
                "Lake Mead",
                "Las Vegas (Nev)",
                "Mojave Desert (Calif)",
                "Western States (US)",
                "your-feed-science"
            ), article.categories
        )
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals("https://www.nytimes.com/2021/06/11/science/drought-las-vegas-grass-mars.html", article.guid)
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