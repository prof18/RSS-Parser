/*
*   Copyright 2019 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof18.rssparser

import com.prof.rssparser.Article
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

/**
 *
 * A generic test, used to check the behaviour of the library with different type of feed.
 * For example to check if a particular feed parsing has an unexpected behaviour
 *
 */
@Ignore
@RunWith(ParameterizedRobolectricTestRunner::class)
class CoreXMLParserGenericTest(private val feedPath: String) {

    private lateinit var articleList: MutableList<Article>

    @Before
    fun setUp() {
        val feed = loadData(feedPath)
        articleList = CoreXMLParser.parseXML(feed)
    }

    @Test
    @Throws
    fun list_isNotEmpty() {
        assertTrue(articleList.isNotEmpty())
    }

    @Test
    @Throws
    fun title_exists() {
        assertNotEquals(articleList[0].title, null)
        if (articleList[0].title != null) {
            assertTrue(articleList[0].title!!.isNotEmpty())
        }
    }

    @Test
    @Throws
    fun author_exists() {
        assertNotEquals(articleList[0].author, null)
        if (articleList[0].author != null) {
            assertTrue(articleList[0].author!!.isNotEmpty())
        }
    }

    @Test
    @Throws
    fun link_exists() {
        assertNotEquals(articleList[0].link, null)
        if (articleList[0].link != null) {
            assertTrue(articleList[0].link!!.isNotEmpty())
        }
    }

    @Test
    @Throws
    fun pubDate_exists() {
        assertNotEquals(articleList[0].pubDate, null)
        if (articleList[0].pubDate != null) {
            assertTrue(articleList[0].pubDate!!.isNotEmpty())
        }
    }

    @Test
    @Throws
    fun description_exists() {
        assertNotEquals(articleList[0].description, null)
        if (articleList[0].description != null) {
            assertTrue(articleList[0].description!!.isNotEmpty())
        }
    }

    @Test
    @Throws
    fun content_exists() {
        assertNotEquals(articleList[0].content, null)
        if (articleList[0].content != null) {
            assertTrue(articleList[0].content!!.isNotEmpty())
        }
    }

    @Test
    @Throws
    fun image_exists() {
        assertNotEquals(articleList[0].image, null)
        if (articleList[0].image != null) {
            assertTrue(articleList[0].image!!.isNotEmpty())
        }
    }

    @Test
    @Throws
    fun categories_notEmpty() {
        assertTrue(articleList[0].categories.isNotEmpty())
    }

    @Test
    @Throws
    fun guid_exists() {
        assertTrue(articleList[0].guid!!.isNotEmpty())
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
                    arrayOf("/feed-test.xml"),
                    arrayOf("/feed-test-time.xml"),
                    arrayOf("/feed-test-image.xml"),
                    arrayOf("/feed-test-image-2.xml"),
                    arrayOf("/feed-test-xsl.xml"),
                    arrayOf("/feed-test-ru.xml"),
                    arrayOf("/feed-test-encoding.xml")
            )
        }
    }
}