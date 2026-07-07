package com.prof18.rssparser

import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.internal.ParserInput
import com.prof18.rssparser.internal.XmlFetcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.fail

class UnsupportedXmlParserTest {

    // Regression test: unsupported input used to throw a Kotlin exception from inside an
    // NSXMLParser delegate callback, unwinding through libxml2's C frames and crashing
    // with EXC_BAD_ACCESS in xmlParseTryOrFinish.
    @Test
    fun whenReceivingUnsupportedXmlTheParserFailsWithoutCrashing() = runTest {
        val html = """
            <!DOCTYPE html>
            <html>
            <head><title>Not a feed</title></head>
            <body><p>Just a web page</p></body>
            </html>
        """.trimIndent()

        val rssParser = RssParser(
            xmlFetcher = UnusedFetcher,
            xmlParser = createXmlParser(),
        )

        assertFailsWith<RssParsingException> {
            rssParser.parse(html)
        }
    }

    @Test
    fun whenReceivingTruncatedXmlTheParserFailsWithoutCrashing() = runTest {
        val truncatedFeed = """
            <?xml version="1.0" encoding="UTF-8"?>
            <rss version="2.0">
            <channel>
            <title>Broken fe
        """.trimIndent()

        val rssParser = RssParser(
            xmlFetcher = UnusedFetcher,
            xmlParser = createXmlParser(),
        )

        assertFailsWith<RssParsingException> {
            rssParser.parse(truncatedFeed)
        }
    }

    private object UnusedFetcher : XmlFetcher {
        override suspend fun fetchXml(url: String): ParserInput = fail("Should not be called")
        override suspend fun fetchXmlAsString(url: String): String = fail("Should not be called")
    }
}
