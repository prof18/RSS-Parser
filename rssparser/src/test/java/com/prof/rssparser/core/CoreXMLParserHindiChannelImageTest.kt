package com.prof.rssparser.core

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserHindiChannelImageTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-hindi-channel-image.xml"
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
        assertEquals(channel.title, "Latest News चीन News18 हिंदी")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(channel.description, "Latest news from चीन Section")
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://hindi.news18.com/rss/khabar/world/china.xml")
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals(channel.image?.title, "News18 हिंदी")
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals(channel.image?.link, "https://hindi.news18.com/rss/khabar/world/china.xml")
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals(channel.image?.url, "https://static.hindi.news18.com/ibnkhabar/uploads/2017/01/mainlogo_hindi_new.png")
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertEquals(channel.image?.description, "Feed provided by News18 हिंदी")
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertNotNull(channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 2)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "कैसे फैला कोरोना वायरस, WHO की टीम करेगी चीनी अधिकारियों के साथ बैठक")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertNull(article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://hindi.news18.com/news/world/who-experts-in-wuhan-probing-coronavirus-origins-meet-chinese-scientists-nodtg-3437941.html")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Friday, January 29, 2021 04:42 PM")
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertFalse(article.description.isNullOrEmpty())
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "https://images.news18.com/ibnkhabar/uploads/2021/01/who-team-1.jpg")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertTrue(article.categories.isEmpty())
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(article.guid, "https://hindi.news18.com/news/world/who-experts-in-wuhan-probing-coronavirus-origins-meet-chinese-scientists-nodtg-3437941.html")
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