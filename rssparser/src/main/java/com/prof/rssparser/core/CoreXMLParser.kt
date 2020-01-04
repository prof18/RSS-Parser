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

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.Image
import com.prof.rssparser.utils.RSSKeywords
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.regex.Pattern

object CoreXMLParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseXML(xml: String): Channel {

        var channelTitle: String? = null
        var channelLink: String? = null
        var channelDescription: String? = null
        var channelImage: Image? = null
        val articleList = mutableListOf<Article>()
        var currentArticle = Article()

        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()
        val inputStream = ByteArrayInputStream(xml.toByteArray())
        xmlPullParser.setInput(inputStream, null)

        // A flag just to be sure of the correct parsing
        var insideItem = false
        var insideChannel = false
        var insideChannelImage = false

        var eventType = xmlPullParser.eventType

        // Start parsing the xml
        while (eventType != XmlPullParser.END_DOCUMENT) {

            // Start parsing the item
            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.name.equals(RSSKeywords.RSS_CHANNEL, ignoreCase = true)) {
                    insideChannel = true

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM, ignoreCase = true)) {
                    insideItem = true

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_CHANNEL_IMAGE, ignoreCase = true)) {
                    insideChannelImage = true
                    channelImage = Image()

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_TITLE, ignoreCase = true)) {
                    if (insideChannel) {
                        when {
                            insideChannelImage -> {
                                channelImage?.title = xmlPullParser.nextText().trim()
                            }
                            insideItem -> {
                                currentArticle.title = xmlPullParser.nextText().trim()
                            }
                            else -> {
                                channelTitle = xmlPullParser.nextText().trim()
                            }
                        }
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_LINK, ignoreCase = true)) {
                    if (insideChannel) {
                        when {
                            insideChannelImage -> {
                                channelImage?.link = xmlPullParser.nextText().trim()
                            }
                            insideItem -> {
                                currentArticle.link = xmlPullParser.nextText().trim()
                            }
                            else -> {
                                channelLink = xmlPullParser.nextText().trim()
                            }
                        }
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_AUTHOR, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.author = xmlPullParser.nextText().trim()
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_CATEGORY, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.addCategory(xmlPullParser.nextText().trim())
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_THUMBNAIL, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.image = xmlPullParser.getAttributeValue(null, RSSKeywords.RSS_ITEM_URL)
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_URL, ignoreCase = true)) {
                    if (insideChannelImage) {
                        channelImage?.url = xmlPullParser.nextText().trim()
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_ENCLOSURE, ignoreCase = true)) {
                    if (insideItem) {
                        val type = xmlPullParser.getAttributeValue(null, RSSKeywords.RSS_ITEM_TYPE)
                        if (type != null && type.contains("image")) {
                            currentArticle.image = xmlPullParser.getAttributeValue(null, RSSKeywords.RSS_ITEM_URL)
                        } else {
                            // let's try if there is the url
                            val url = xmlPullParser.getAttributeValue(null, RSSKeywords.RSS_ITEM_URL)
                            if (url != null) {
                                currentArticle.image = url
                            }
                        }
                    }

                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_DESCRIPTION, ignoreCase = true)) {
                    if (insideChannel) {
                        if (insideItem) {
                            val description = xmlPullParser.nextText()
                            currentArticle.description = description.trim()
                            if (currentArticle.image == null) {
                                currentArticle.image = getImageUrl(description)
                            }
                        } else if (insideChannelImage) {
                            channelImage?.description = xmlPullParser.nextText().trim()
                        } else {
                            channelDescription = xmlPullParser.nextText().trim()
                        }
                    }

            } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_CONTENT, ignoreCase = true)) {
                if (insideItem) {
                    val content = xmlPullParser.nextText().trim()
                    currentArticle.content = content
                    if (currentArticle.image == null) {
                        currentArticle.image = getImageUrl(content)
                    }
                }

            } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_PUB_DATE, ignoreCase = true)) {
                if (insideItem) {
                    val nextTokenType = xmlPullParser.next()
                    if (nextTokenType == XmlPullParser.TEXT) {
                        currentArticle.pubDate = xmlPullParser.text.trim()
                    }
                    // Skip to be able to find date inside 'tag' tag
                    continue
                }

            } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_TIME, ignoreCase = true)) {
                if (insideItem) {
                    currentArticle.pubDate = xmlPullParser.nextText()
                }

            } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_GUID, ignoreCase = true)) {
                if (insideItem) {
                    currentArticle.guid = xmlPullParser.nextText().trim()
                }
            }

        } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals(RSSKeywords.RSS_ITEM, ignoreCase = true)) {
            // The item is correctly parsed
            insideItem = false
            articleList.add(currentArticle)
            currentArticle = Article()
        } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals(RSSKeywords.RSS_CHANNEL, ignoreCase = true)) {
            // The channel is correctly parsed
            insideChannel = false
        } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals(RSSKeywords.RSS_CHANNEL_IMAGE, ignoreCase = true)) {
            // The channel image is correctly parsed
            insideChannelImage = false
        }
        eventType = xmlPullParser.next()
    }
    return Channel(channelTitle, channelLink, channelDescription, channelImage, articleList)
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
            url = matcherLink.group(1).trim()
        }
    }
    return url
}
}
