package com.prof18.rssparser

import com.prof.rssparser.Article
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class CoreXMLParserGenericTest(private val feedPath: String) {

    private lateinit var articleList: MutableList<Article>

    @Before
    fun setUp() {
        val feed = loadData(feedPath)
        articleList = CoreXMLParser.parseXML(feed)
    }

    // TODO: checks that all the items is not null
    // TODO: in the specific test, check if the first item is equal to what expected

    @Test
    @Throws
    fun list_isNotEmpty() {
        Assert.assertTrue(articleList.isNotEmpty())
    }

    private fun loadData(path: String): String {
        val inputStream = javaClass.getResourceAsStream(path)!!
        return inputStream.bufferedReader().use { it.readText() }
    }


    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters
        fun data(): Collection<Any> {
            return listOf(
                    arrayOf("/feed-test.xml"),         // First test:  (paramOne = 1, paramTwo = "I")
                    arrayOf("/feed-test-time.xml"), // Second test: (paramOne = 1999, paramTwo = "MCMXCIX")
                    arrayOf("/feed-test-image.xml") // Second test: (paramOne = 1999, paramTwo = "MCMXCIX")
            )
        }
    }
}