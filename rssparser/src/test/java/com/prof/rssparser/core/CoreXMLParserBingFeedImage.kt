package com.prof.rssparser.core

import android.os.Build
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CoreXMLParserBingFeedImage {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-bing-image.xml"
    private lateinit var channel: Channel

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        val feed = inputStream.bufferedReader().use { it.readText() }
        channel = CoreXMLParser.parseXML(feed)
        articleList = channel.articles
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals(channel.title, "esparragosa - BingNews")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(channel.description, "Search results")
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://www.bing.com:443/news/search?q=esparragosa&format=rss")
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals(channel.image?.title, "esparragosa")
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals(channel.image?.link, "https://www.bing.com:443/news/search?q=esparragosa&format=rss")
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals(channel.image?.url, "http://www.bing.com/rsslogo.gif")
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
}