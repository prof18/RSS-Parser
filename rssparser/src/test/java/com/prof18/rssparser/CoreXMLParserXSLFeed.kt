package com.prof18.rssparser

import com.prof.rssparser.Article
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserXSLFeed {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-xsl.xml"

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        val feed = inputStream.bufferedReader().use { it.readText() }
        articleList = CoreXMLParser.parseXML(feed)
        article = articleList[0]
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 20)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "Insight: Who should Liverpool sign?")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://www.skysports.com/football/news/11669/11719097/premier-league-transfer-window-who-should-liverpool-sign")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Fri, 17 May 2019 06:00:00 BST")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "Liverpool just missed out on clinching the Premier League title and have a Champions League final to look forward to - so where could they improve?")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "https://e2.365dm.com/19/04/128x67/skysports-jurgen-klopp-liverpool_4654732.jpg?20190430113948")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf(
                "News Story"
        ))
    }
}