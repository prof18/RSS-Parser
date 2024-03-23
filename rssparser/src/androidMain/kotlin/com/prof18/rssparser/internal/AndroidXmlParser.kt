/*
*   Copyright 2016 Marco Gomiero
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

package com.prof18.rssparser.internal

import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.internal.atom.extractAtomContent
import com.prof18.rssparser.internal.rss.extractRSSContent
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.nio.charset.Charset

internal class AndroidXmlParser(
    private val charset: Charset? = null,
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {

    override suspend fun parseXML(input: ParserInput): RssChannel = withContext(dispatcher) {

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = false

            val xmlPullParser = factory.newPullParser()

            // If the charset is null, then the parser will infer it from the feed
            xmlPullParser.setInput(input.inputStream, charset?.toString())

            var rssChannel: RssChannel? = null

            var eventType = xmlPullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xmlPullParser.contains(RssKeyword.Rss)) {
                        rssChannel = extractRSSContent(xmlPullParser)
                    } else if (xmlPullParser.contains(AtomKeyword.Atom)) {
                        rssChannel = extractAtomContent(xmlPullParser)
                    }
                }
                eventType = xmlPullParser.next()
            }

            return@withContext rssChannel
                ?: throw IllegalArgumentException(
                    "The provided XML is not supported. Only RSS and Atom feeds are supported",
                )
        } catch (exception: XmlPullParserException) {
            throw RssParsingException(
                message = "Something went wrong during the parsing of the feed. Please check if the XML is valid",
                cause = exception
            )
        } finally {
            input.inputStream.closeQuietly()
        }
    }

    override fun generateParserInputFromString(rawRssFeed: String): ParserInput {
        val cleanedXml = rawRssFeed.trim()
        val inputStream: InputStream = cleanedXml.byteInputStream(charset ?: Charsets.UTF_8)
        return ParserInput(inputStream)
    }
}
