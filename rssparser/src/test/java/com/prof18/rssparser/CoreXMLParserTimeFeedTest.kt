package com.prof18.rssparser

import com.prof.rssparser.Article
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserTimeFeedTest {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-time.xml"

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
        assertEquals(article.title, "Vivimed Life Sciences Pvt Ltd Issues Voluntary Nationwide Recall of Losartan\n" +
                "                Potassium 25 mg, 50 mg and 100 mg Tablets, USP Due to the Detection of Trace Amounts\n" +
                "                of N-Nitroso-N-methyl-4-aminobutyric acid (NMBA) Impurity")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, "FDA")
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "http://www.fda.gov/safety/recalls-market-withdrawals-safety-alerts/vivimed-life-sciences-pvt-ltd-issues-voluntary-nationwide-recall-losartan-potassium-25-mg-50-mg-and")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Fri, 05/03/2019 - 15:21")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "Vivimed Life Sciences Pvt Ltd (Vivimed) is recalling 19 lots of Losartan\n" +
                "                Potassium Tablets USP 25 mg, 50 mg, and 100 mg to consumer level. Due to the\n" +
                "                detection of an impurity – N-Nitroso-N-methyl-4-aminobutyric acid (NMBA) – that is\n" +
                "                above the US Food & Drug Administration’s interim acceptable exposu")
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