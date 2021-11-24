package com.prof.rssparser.core

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.nio.charset.Charset

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserAtomExampleTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/atom-test-example.xml"
    private lateinit var channel: Channel

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        val feed = inputStream.bufferedReader(Charset.forName("UTF-8")).use { it.readText() }
        channel = CoreXMLParser.parseXML(feed)
        articleList = channel.articles
        article = articleList[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals("Example Feed", channel.title)
    }

    @Test
    fun channelSubtitle_isCorrect() {
        assertEquals("A subtitle.", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("http://example.org/", channel.link)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals("Atom-Powered Robots Run Amok", article.title)
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals("John Doe", article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals("http://example.org/2003/12/13/atom03.html", article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("2003-12-13T18:30:02Z", article.pubDate)
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertEquals("Some text.", article.description)
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertNull(article.content)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(
            "urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a",
            article.guid
        )
    }
}