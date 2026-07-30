package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * RSS-Bridge serves the thumbnail from an image endpoint with no file extension, so the image can
 * only be recovered from the `src` of the img tag in the content.
 */
class XmlParserAtomRssBridgeTest : XmlParserTestExecutor() {

    @Test
    fun imageWithoutFileExtensionIsParsedFromContent() = runTest {
        val channel = parseFeed("atom-feed-rss-bridge.xml")

        assertEquals(
            "https://api.ardmediathek.de/image-service/images/" +
                "urn:ard:image:c9c909df6f5a4b3f?w=432&ch=ce51a6b849fde683",
            channel.items.first().image
        )
    }
}
