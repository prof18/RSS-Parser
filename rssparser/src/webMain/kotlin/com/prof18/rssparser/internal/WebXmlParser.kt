package com.prof18.rssparser.internal

import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.internal.entity.AtomFeedEntity
import com.prof18.rssparser.internal.entity.FeedEntity
import com.prof18.rssparser.internal.entity.RdfFeedEntity
import com.prof18.rssparser.internal.entity.RssFeedEntity
import com.prof18.rssparser.internal.mapper.toRssChannel
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import nl.adaptivity.xmlutil.EventType
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.xmlStreaming

internal class WebXmlParser(
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {
    private val feedModule = SerializersModule {
        polymorphic(FeedEntity::class) {
            subclass(AtomFeedEntity::class)
            subclass(RssFeedEntity::class)
            subclass(RdfFeedEntity::class)
        }
    }
    private val xml = XML(feedModule) {
        defaultPolicy {
            ignoreUnknownChildren()
            autoPolymorphic = true
            isStrictBoolean = false
            isStrictAttributeNames = false
            isStrictOtherAttributes = false
        }
        indent = 4
    }

    override suspend fun parseXML(input: ParserInput): RssChannel = withContext(dispatcher) {
        val feedEntity = try {
            decodeFeed(input.data)
        } catch (_: Throwable) {
            // Fallback to handle XHTML/HTML content and mismatched HTML tags in content/description tags
            val cleanedData = input.data
                .replace(Regex("<content([^>]*?)>([\\s\\S]*?)</content>")) { matchResult ->
                    val attributes = matchResult.groupValues[1]
                    val content = matchResult.groupValues[2]
                    val cleanedContent = content
                        .replace(Regex("<[^>]*>"), "") // Remove all XML/HTML tags
                        .replace(Regex("&[^;]+;"), "") // Remove HTML entities
                        .replace(Regex("\\s+"), " ") // Normalize whitespace
                        .trim()
                    "<content$attributes>$cleanedContent</content>"
                }
                .replace(Regex("<description>([\\s\\S]*?)</description>")) { matchResult ->
                    val content = matchResult.groupValues[1]
                    val cleanedContent = content
                        .replace(Regex("<[^>]*>"), "") // Remove all XML/HTML tags (including mismatched ones)
                        .replace(Regex("&[^;]+;"), "") // Remove HTML entities
                        .replace(Regex("\\s+"), " ") // Normalize whitespace
                        .trim()
                    "<description>$cleanedContent</description>"
                }
            try {
                decodeFeed(cleanedData)
            } catch (exception: Exception) {
                throw RssParsingException(
                    message = "Something went wrong when parsing the feed. Please check if the XML is valid",
                    cause = exception
                )
            }
        }
        return@withContext feedEntity.toRssChannel(input.baseUrl)
    }

    /**
     * The browser's DOMParser doesn't throw on malformed XML (a bare `&`, an HTML entity like
     * `&auml;`): it stops at the error and puts a `parsererror` element in the document. Decoding
     * that document gives an empty or truncated feed, so the error is turned into an exception here,
     * which lets the caller retry with the escaped XML like on the other platforms.
     */
    private fun decodeFeed(data: String): FeedEntity {
        if (hasParserError(data)) {
            throw IllegalArgumentException("The XML is not well formed")
        }
        return xml.decodeFromString<FeedEntity>(data)
    }

    private fun hasParserError(data: String): Boolean {
        val reader = xmlStreaming.newReader(data)
        try {
            while (reader.hasNext()) {
                if (reader.next() == EventType.START_ELEMENT && reader.localName == PARSER_ERROR_TAG) {
                    return true
                }
            }
            return false
        } finally {
            reader.close()
        }
    }

    private companion object {
        private const val PARSER_ERROR_TAG = "parsererror"
    }

    override fun generateParserInputFromString(rawRssFeed: String): ParserInput =
        ParserInput(
            data = rawRssFeed,
            baseUrl = null
        )
}
