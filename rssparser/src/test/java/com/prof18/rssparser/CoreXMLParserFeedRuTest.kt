package com.prof18.rssparser

import com.prof.rssparser.Article
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserFeedRuTest {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-ru.xml"

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
        assertEquals(articleList.size, 100)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "СМИ: собака в Таиланде спасла заживо похороненного младенца")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "http://www.aif.ru/incidents/smi_sobaka_v_tailande_spasla_zazhivo_pohoronennogo_mladenca")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Sat, 18 May 2019 10:52:50 +0300")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "15-летняя мать ребенка решила избавиться от него, побоявшись гнева родителей")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "https://images.aif.ru/017/020/025bc5cb4cc5d0f8347bbb27f5e4d13b.jpg")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf(
                "Происшествия"
        ))
    }
}