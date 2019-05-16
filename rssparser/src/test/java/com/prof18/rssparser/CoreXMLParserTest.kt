package com.prof18.rssparser

import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class CoreXMLParserTest {

    private val feedPath = "/feed-test.xml"
    private val timeFeedPath = "/feed-test-time.xml"

    private lateinit var feed: String
    private lateinit var timeFeed: String

    @Before
    fun setUp() {
        feed = loadData(feedPath)
        timeFeed = loadData(timeFeedPath)
    }

    /**
     *
     * "Standard" feed from Android Authority
     *
     */
    @Test
    @Throws
    fun feed_list_isNotEmpty() {
        val articleList = CoreXMLParser.parseXML(feed)
        assertTrue(articleList.isNotEmpty())
    }

    /**
     *
     * Feed with strange time
     *
     */
    @Test
    @Throws
    fun time_feed_list_isNotEmpty() {
        val articleList = CoreXMLParser.parseXML(timeFeed)
        assertTrue(articleList.isNotEmpty())
    }

    private fun loadData(path: String): String {
        val inputStream = CoreXMLParserTest::class.java.getResourceAsStream(path)!!
        return inputStream.bufferedReader().use { it.readText() }
    }


}
