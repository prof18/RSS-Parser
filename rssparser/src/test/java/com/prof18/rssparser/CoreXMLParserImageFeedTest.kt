package com.prof18.rssparser

import com.prof.rssparser.Article
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserImageFeedTest {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-image.xml"

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
        assertEquals(articleList.size, 50)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "The Sun Is Also a Star Review: Yara Shahidi & Charles Melton Elevate Teen Romance")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://movieweb.com/the-sun-is-also-a-star-review/")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Wed, 15 May 2019 16:52:24 PDT")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "The Sun Is Also a Star is a diverse romance that bucks Hollywood's YA genre.")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)  }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, null)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf<String>())
    }
}