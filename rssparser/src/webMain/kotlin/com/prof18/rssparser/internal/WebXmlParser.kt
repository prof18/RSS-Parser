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
import nl.adaptivity.xmlutil.serialization.XML

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
            xml.decodeFromString<FeedEntity>(input.data)
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
                xml.decodeFromString<FeedEntity>(cleanedData)
            } catch (exception: Exception) {
                throw RssParsingException(
                    message = "Something went wrong when parsing the feed. Please check if the XML is valid",
                    cause = exception
                )
            }
        }
        return@withContext feedEntity.toRssChannel(input.baseUrl)
    }

    override fun generateParserInputFromString(rawRssFeed: String): ParserInput =
        ParserInput(
            data = rawRssFeed,
            baseUrl = null
        )
}
