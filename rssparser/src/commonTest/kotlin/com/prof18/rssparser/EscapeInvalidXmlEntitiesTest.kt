package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import com.prof18.rssparser.internal.XmlFetcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EscapeInvalidXmlEntitiesTest : XmlParserTestExecutor() {

    /**
     * A feed URL pointing at a website hands the escaping fallback a full HTML page, which is one
     * very long line. The test passes or it times out, there is nothing in between.
     */
    @Test
    fun escapingALargeMinifiedHtmlPageTerminates() = runTest {
        val html = buildMinifiedHtmlPage()
        assertTrue(html.length > 800_000, "expected a page big enough to be representative")
        assertEquals(0, html.count { it == '\n' }, "the page has to be one single line")

        val escaped = rssParser().escapeInvalidXmlEntities(html)

        assertFalse(BARE_AMPERSAND_REGEX.containsMatchIn(escaped))
    }

    @Test
    fun bareAmpersandsAreEscapedEverywhereIncludingCdataAndAttributes() = runTest {
        val xml = """
            <item>
                <link>https://example.com/a?ref=home&pos=1</link>
                <enclosure url="https://cdn.example.com/a.mp3?token=x&exp=2" type="audio/mpeg"/>
                <description><![CDATA[Tom & Jerry, R&D and Q&A]]></description>
            </item>
        """.trimIndent()

        val escaped = rssParser().escapeInvalidXmlEntities(xml)

        assertTrue(escaped.contains("?ref=home&amp;pos=1"))
        assertTrue(escaped.contains("?token=x&amp;exp=2"))
        assertTrue(escaped.contains("Tom &amp; Jerry, R&amp;D and Q&amp;A"))
        assertFalse(BARE_AMPERSAND_REGEX.containsMatchIn(escaped))
    }

    @Test
    fun alreadyValidEntitiesAreLeftAlone() = runTest {
        val xml = "<title>A &amp; B &lt;C&gt; &quot;D&quot; &apos;E&apos; &#38; &#x26;</title>"

        assertEquals(xml, rssParser().escapeInvalidXmlEntities(xml))
    }

    @Test
    fun htmlNamedEntitiesAreTurnedIntoNumericReferences() = runTest {
        val xml = "<title>Myrskyl&auml; &Auml;&Ouml; caf&eacute;&nbsp;&mdash; 5&euro; &hellip;</title>"

        val escaped = rssParser().escapeInvalidXmlEntities(xml)

        assertEquals(
            "<title>Myrskyl&#228; &#196;&#214; caf&#233;&#160;&#8212; 5&#8364; &#8230;</title>",
            escaped,
        )
    }

    @Test
    fun unknownNamedEntitiesAreEscapedAsText() = runTest {
        val xml = "<title>&notanentity; &AUML;</title>"

        val escaped = rssParser().escapeInvalidXmlEntities(xml)

        assertEquals("<title>&amp;notanentity; &amp;AUML;</title>", escaped)
    }

    @Test
    fun aFeedWithHtmlNamedEntitiesIsParsedWithTheRightCharacters() = runTest {
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <rss version="2.0">
                <channel>
                    <title>Myrskyl&auml;</title>
                    <link>https://myrskyla.fi</link>
                    <description>Kunta</description>
                    <item>
                        <title>&Auml;&auml;nestys &ndash; tulokset</title>
                        <link>https://myrskyla.fi/a?x=1&y=2</link>
                        <description>Caf&eacute; &amp; ravintola</description>
                    </item>
                </channel>
            </rss>
        """.trimIndent()

        val channel = rssParser().parse(xml)

        assertEquals("Myrskylä", channel.title)
        val item = channel.items.single()
        assertEquals("Äänestys – tulokset", item.title)
        assertEquals("https://myrskyla.fi/a?x=1&y=2", item.link)
        assertEquals("Café & ravintola", item.description)
    }

    @Test
    fun aFeedWithABareAmpersandIsParsedOnEveryPlatform() = runTest {
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <rss version="2.0">
                <channel>
                    <title>Tom & Jerry</title>
                    <item><title>First</title></item>
                    <item><title>Second</title></item>
                </channel>
            </rss>
        """.trimIndent()

        val channel = rssParser().parse(xml)

        assertEquals("Tom & Jerry", channel.title)
        assertEquals(listOf("First", "Second"), channel.items.map { it.title })
    }

    private fun rssParser(): RssParser = RssParser(
        xmlFetcher = object : XmlFetcher {
            override suspend fun fetchXml(url: String): ParserInput =
                error("not used by these tests")

            override suspend fun fetchXmlAsString(url: String): String =
                error("not used by these tests")
        },
        xmlParser = createXmlParser()
    )

    /**
     * Roughly what a news homepage looks like to the parser: no newlines, and thousands of
     * `attribute="…"` pairs holding query strings with bare ampersands in them.
     */
    private fun buildMinifiedHtmlPage(): String = buildString {
        append("<!DOCTYPE html><html lang=\"de\"><head><meta charset=\"utf-8\">")
        append("<title>Nachrichten</title></head><body>")
        repeat(HTML_ELEMENT_COUNT) { index ->
            append("<div class=\"teaser\" data-id=\"$index\">")
            append("<a href=\"https://www.example.com/artikel-$index?ref=home&pos=$index&t=live\">")
            append("<img src=\"https://img.example.com/$index.jpg?w=640&h=360\" alt=\"Bild $index\">")
            append("Schlagzeile $index &mdash; mehr dazu")
            append("</a></div>")
        }
        append("</body></html>")
    }

    private companion object {
        // ~4.500 elements of ~200 chars lands around 900 KB, the size of a real news homepage.
        private const val HTML_ELEMENT_COUNT = 4_500
        private val BARE_AMPERSAND_REGEX =
            Regex("&(?!(amp;|lt;|gt;|quot;|apos;|#[0-9]+;|#x[0-9a-fA-F]+;))")
    }
}
