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
        article = articleList[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals(channel.title, "madrid - BingNews")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(channel.description, "Search results")
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://www.bing.com:443/news/search?q=madrid&format=rss")
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals(channel.image?.title, "madrid")
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals(channel.image?.link, "https://www.bing.com:443/news/search?q=madrid&format=rss")
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

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 1)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "Real Madrid, Barcelona and Juventus 'threaten clubs withdrawing from Super League\n" +
                "                with legal action'")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "http://www.bing.com/news/apiclick.aspx?ref=FexRss&aid=&tid=12BE3CE268B0484F92DD9828C685E325&url=https%3a%2f%2fwww.dailymail.co.uk%2fsport%2ffootball%2farticle-9554407%2fReal-Madrid-Barcelona-Juventus-threaten-clubs-withdrawing-Super-League-legal-action.html&c=4438839993362862681&mkt=it-it")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Fri, 07 May 2021 12:00:00 GMT")
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertEquals(article.description, "The three European giants are refusing to let the plans die and have\n" +
                "                warned their former partners they will extract millions of dollars in penalties if\n" +
                "                they walk away from the league.")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "http://www.bing.com/th?id=OVFT.SNpH_QAbpZOYgrEHZRyCTi&pid=News")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, listOf<String>())
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
}