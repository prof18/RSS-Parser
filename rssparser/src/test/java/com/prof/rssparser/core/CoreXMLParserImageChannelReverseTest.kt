package com.prof.rssparser.core

import android.os.Build
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CoreXMLParserImageChannelReverseTest {
    private lateinit var articleList: MutableList<Article>
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
        Assert.assertEquals(channel.title, "The Joe Rogan Experience")
    }

    @Test
    fun channelDesc_isCorrect() {
        Assert.assertEquals(channel.description, "The podcast of Comedian Joe Rogan..")
    }

    @Test
    fun channelLink_isCorrect() {
        Assert.assertEquals(channel.link, "https://www.joerogan.com")
    }

    @Test
    fun channelImageTitle_isCorrect() {
        Assert.assertEquals(channel.image?.title, "The Joe Rogan Experience")
    }

    @Test
    fun channelImageLink_isCorrect() {
        Assert.assertEquals(channel.image?.link, "https://www.joerogan.com")
    }

    @Test
    fun channelImageUrl_isCorrect() {
        Assert.assertEquals(channel.image?.url, "http://static.libsyn.com/p/assets/7/1/f/3/71f3014e14ef2722/JREiTunesImage2.jpg")
    }

    @Test
    fun channelImageDescription_isCorrect() {
        Assert.assertNull(channel.image?.description)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals(channel.lastBuildDate, "Sat, 04 Jan 2020 01:06:48 +0000")
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        Assert.assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        Assert.assertEquals(articleList.size, 6)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        Assert.assertEquals(article.title, "#1405 - Sober October 3 Recap")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        Assert.assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        Assert.assertEquals(article.link, "http://traffic.libsyn.com/joeroganexp/p1405.mp3")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        Assert.assertEquals(article.pubDate, "Tue, 24 Dec 2019 20:00:00 +0000")
    }

    @Test
    @Throws
    fun description_isPresent() {
        Assert.assertEquals(article.description, "Joe is joined by Ari Shaffir, Bert Kreischer & Tom Segura to recap their 3rd annual Sober October challenge.")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        Assert.assertEquals(article.content, "Joe is joined by Ari Shaffir, Bert Kreischer & Tom Segura to recap their 3rd annual Sober October challenge.")
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertNull(article.image)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
       assert(article.categories.isEmpty())
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        Assert.assertEquals(article.guid, "0d7147a3-f1c1-4ae6-bbf8-2e0a493639ca")
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        Assert.assertEquals(article.audio, "http://traffic.libsyn.com/joeroganexp/p1405.mp3?dest-id=19997")
    }

    @Test
    @Throws
    fun sourceName_iCorrect() {
        Assert.assertNull(article.sourceName)
    }

    @Test
    @Throws
    fun sourceUrl_iCorrect() {
        Assert.assertNull(article.sourceUrl)
    }

    @Test
    @Throws
    fun video_isCorrect() {
        Assert.assertNull(article.video)
    }
}