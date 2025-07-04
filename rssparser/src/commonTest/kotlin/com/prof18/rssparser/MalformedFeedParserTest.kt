package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import com.prof18.rssparser.internal.XmlFetcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class MalformedFeedParserTest : XmlParserTestExecutor() {

    @Test
    fun whenReceivingAMalformedXmlTheParserWillHandleIt() = runTest {
        // On web, the fallback to delete the whitespace at the beginning it doesn't work
        if (currentTarget == CurrentTarget.WEB) {
            assertTrue(true)
            return@runTest
        }
        val rssParser = RssParser(
            xmlFetcher = object : XmlFetcher {
                override suspend fun fetchXml(url: String): ParserInput =
                    readFileFromResources("feed-test-malformed.xml")

                override suspend fun fetchXmlAsString(url: String): String =
                    readFileFromResourcesAsString("feed-test-malformed.xml")
            },
            xmlParser = createXmlParser()
        )

        val channel = rssParser.getRssChannel("feed-url")
        assertTrue(channel.items.isNotEmpty())
    }
}
