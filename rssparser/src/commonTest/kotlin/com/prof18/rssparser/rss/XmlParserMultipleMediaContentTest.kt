package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserMultipleMediaContentTest : XmlParserTestExecutor() {

    @Test
    @Suppress("DEPRECATION")
    fun preservesEveryMediaContentElementAndItsDimensions() = runTest {
        val item = parseFeed("feed-media-content-multiple-sizes.xml").items.single()

        assertEquals(
            listOf(140, 460, 700),
            item.rawMediaContents.map { it.width },
        )
        assertEquals(
            listOf(79, 259, 394),
            item.rawMediaContents.map { it.height },
        )
        assertEquals("https://example.com/image-140.jpg", item.image)
        assertEquals(item.rawMediaContents.first(), item.rawMediaContent)
    }
}
