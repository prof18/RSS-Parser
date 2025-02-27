package com.prof18.rssparser

import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.internal.XmlFetcher
import com.prof18.rssparser.internal.XmlParser
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

public class RssParser internal constructor(
    private val xmlFetcher: XmlFetcher,
    private val xmlParser: XmlParser,
) {

    private val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Default

    internal interface Builder {
        /**
         * Creates a [RssParser] object
         */
        fun build(): RssParser
    }

    /**
     * Downloads and parses an RSS feed from an [url] and returns an [RssChannel].
     *
     * If the parsing fails because the XML is malformed, it will re-download the XML as a string,
     * escape any invalid XML entities, and try to parse it again. If it still fails, it will
     * throw an [RssParsingException].
     */
    public suspend fun getRssChannel(url: String): RssChannel = withContext(coroutineContext) {
        val parserInput = xmlFetcher.fetchXml(url)
        return@withContext try {
            xmlParser.parseXML(parserInput)
        } catch (_: RssParsingException) {
            val xmlAsString = xmlFetcher.fetchXmlAsString(url)
            val escapedXml = escapeInvalidXmlEntities(xmlAsString)
            val escapedInput = xmlParser.generateParserInputFromString(escapedXml)
            xmlParser.parseXML(escapedInput)
        }
    }

    /**
     * Parses an RSS feed provided by [rawRssFeed] and returns an [RssChannel]
     */
    public suspend fun parse(rawRssFeed: String): RssChannel = withContext(coroutineContext) {
        val parserInput = xmlParser.generateParserInputFromString(rawRssFeed)
        return@withContext try {
            xmlParser.parseXML(parserInput)
        } catch (_: RssParsingException) {
            // Try again with additional entity escaping
            val escapedXml = escapeInvalidXmlEntities(rawRssFeed)
            val input = xmlParser.generateParserInputFromString(escapedXml)
            xmlParser.parseXML(input)
        }
    }

    /**
     * Escapes invalid XML entities in the input string and fixes common XML issues.
     * - Escapes standalone ampersands that aren't part of valid entity references
     * - Fixes self-closing or unclosed tags that should be properly closed
     * - Fixes duplicate closing tags with content between them
     */
    private fun escapeInvalidXmlEntities(xml: String): String {
        return xml
            // Fix standalone ampersands
            .replace(
                Regex("&(?!(amp;|lt;|gt;|quot;|apos;|#[0-9]+;|#x[0-9a-fA-F]+;))"),
                "&amp;"
            )
            // Fix duplicate closing tags with content between them
            // Example: <category></category><![CDATA[News]]></category> -> <category><![CDATA[News]]></category>
            .replace(
                Regex("<([^>]+)></\\1>([^<]+|<!\\[CDATA\\[.+?\\]\\]>)</\\1>"),
                "<$1>$2</$1>"
            )
            // Fix self-closing tags, but only if they don't already have content
            // This regex checks that there's no content between the opening and closing tags
            .replace(
                Regex("<(link|source|category|guid|enclosure|media:content|media:thumbnail)([^>]*?)>\\s*</\\1>"),
                "<$1$2></$1>"
            )
            // Fix other common HTML tags that might be self-closing
            .replace(
                Regex("<(meta|img|br|hr|input|area|base|col|embed|keygen|param|track|wbr)([^>]*?)/?>(?!</\\1>)"),
                "<$1$2></$1>"
            )
    }
}

/**
 * Returns a default [RssParser] instance.
 *
 * Check the platform specific RssParserBuilder for details on the default behaviour.
 */
public expect fun RssParser(): RssParser
