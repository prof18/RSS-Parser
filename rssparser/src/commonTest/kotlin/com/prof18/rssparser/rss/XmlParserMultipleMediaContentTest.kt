package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RawMediaContent
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

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

    @Test
    fun keepsMediaContentThatOnlyHasDimensions() = runTest {
        val item = parseFeed("feed-media-content-dimensions-only.xml").items.single()

        assertEquals(
            listOf(RawMediaContent(url = null, type = null, medium = null, width = 640, height = 360)),
            item.rawMediaContents,
        )
    }

    @Test
    @Suppress("DEPRECATION")
    fun deprecatedAccessorAlwaysFollowsTheList() = runTest {
        val item = parseFeed("feed-media-content-multiple-sizes.xml").items.single()

        val emptied = item.copy(rawMediaContents = emptyList())
        assertNull(emptied.rawMediaContent)

        val replacement = RawMediaContent(url = "https://example.com/other.jpg", type = null, medium = "image")
        val replaced = item.copy(rawMediaContents = listOf(replacement))
        assertEquals(replacement, replaced.rawMediaContent)
    }
}
