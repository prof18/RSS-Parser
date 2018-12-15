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

package com.prof.rssparser.utils

import com.prof.rssparser.Article
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object CoreXMLParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseXML(xml: String): MutableList<Article> {

        val articleList = mutableListOf<Article>()
        var currentArticle = Article()

        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()
        xmlPullParser.setInput(StringReader(xml))

        // A flag just to be sure of the correct parsing
        var insideItem = false

        var eventType = xmlPullParser.eventType

        // Start parsing the xml
        while (eventType != XmlPullParser.END_DOCUMENT) {

            // Start parsing the item
            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM, ignoreCase = true)) {
                    insideItem = true

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_TITLE, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.title = xmlPullParser.nextText()
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_LINK, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.link = xmlPullParser.nextText()
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_AUTHOR, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.author = xmlPullParser.nextText()
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_CATEGORY, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.addCategory(xmlPullParser.nextText())
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_THUMBNAIL, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.image = xmlPullParser.getAttributeValue(null, RSSKeywords.RSS_ITEM_URL)
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_DESCRIPTION, ignoreCase = true)) {
                    if (insideItem) {
                        val description = xmlPullParser.nextText()
                        currentArticle.description = description
                        if (currentArticle.image == null) {
                            currentArticle.image = getImageUrl(description)
                        }
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_CONTENT, ignoreCase = true)) {
                    if (insideItem) {
                        val content = xmlPullParser.nextText()
                        currentArticle.content = content
                        if (currentArticle.image == null) {
                            currentArticle.image = getImageUrl(content)
                        }
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_PUB_DATE, ignoreCase = true)) {
                    // RSS date format is the following: Sat, 15 Dec 2018 12:00:32 +0000
                    // https://validator.w3.org/feed/docs/rss2.html
                    val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.getDefault())
                    currentArticle.pubDate = sdf.parse(xmlPullParser.nextText())
                }

            } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals("item", ignoreCase = true)) {
                // The item is correctly parsed
                insideItem = false
                articleList.add(currentArticle)
                currentArticle = Article()
            }
            eventType = xmlPullParser.next()
        }
        return articleList
    }

    /**
     * Finds the first img tag and get the src as featured image
     *
     * @param input The content in which to search for the tag
     * @return The url, if there is one
     */
    private fun getImageUrl(input: String): String? {

        var url: String? = null
        val patternImg = Pattern.compile("(<img .*?>)")
        val matcherImg = patternImg.matcher(input)
        if (matcherImg.find()) {
            val imgTag = matcherImg.group(1)
            val patternLink = Pattern.compile("src\\s*=\\s*\"(.+?)\"")
            val matcherLink = patternLink.matcher(imgTag)
            if (matcherLink.find()) {
                url = matcherLink.group(1)
            }
        }
        return url
    }
}