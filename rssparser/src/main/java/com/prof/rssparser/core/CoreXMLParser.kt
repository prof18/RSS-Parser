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

package com.prof.rssparser.core

import com.prof.rssparser.Channel
import com.prof.rssparser.core.parser.atom.extractAtomContent
import com.prof.rssparser.core.parser.rss.extractRSSContent
import com.prof.rssparser.utils.AtomKeyword
import com.prof.rssparser.utils.RSSKeyword
import com.prof.rssparser.utils.contains
import okhttp3.internal.closeQuietly
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.nio.charset.Charset
import java.util.regex.Pattern

internal object CoreXMLParser {

    fun parseXML(inputStream: InputStream, charset: Charset? = null): Channel {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()

        // If the charset is null, then the parser will infer it from the feed
        xmlPullParser.setInput(inputStream, charset?.toString())

        var eventType = xmlPullParser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.contains(RSSKeyword.RSS)) {
                    return extractRSSContent(xmlPullParser)
                } else if (xmlPullParser.contains(AtomKeyword.ATOM)) {
                    return extractAtomContent(xmlPullParser)
                }
            }
            eventType = xmlPullParser.next()
        }

        inputStream.closeQuietly()

        throw IllegalArgumentException("XmlPullParser not support the xml content.")
    }

    /**
     * Finds the first img tag and get the src as featured image
     *
     * @param input The content in which to search for the tag
     * @return The url, if there is one
     */
    internal fun getImageUrl(input: String?): String? {
        var url: String? = null
        val patternImg = Pattern.compile("(<img .*?>)")
        val matcherImg = patternImg.matcher(input ?: "")
        if (matcherImg.find()) {
            val imgTag = matcherImg.group(1)
            val patternLink = Pattern.compile("src\\s*=\\s*([\"'])(.+?)([\"'])")
            val matcherLink = patternLink.matcher(imgTag ?: "")
            if (matcherLink.find()) {
                url = matcherLink.group(2)?.trim()
            }
        }
        return url
    }
}
