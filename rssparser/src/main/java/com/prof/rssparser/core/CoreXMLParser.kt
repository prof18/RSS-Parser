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
import com.prof.rssparser.utils.RSSKeyword
import com.prof.rssparser.utils.attributeValue
import com.prof.rssparser.utils.contains
import com.prof.rssparser.utils.nextTrimmedText
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.io.Reader
import java.util.regex.Pattern

internal object CoreXMLParser {

    fun parseXML(xml: String): Channel {

        var articleBuilder = Article.Builder()
        val channelImageBuilder = Image.Builder()
        val channelBuilder = Channel.Builder()

        // This image url is extracted from the content and the description of the rss item.
        // It's a fallback just in case there aren't any images in the enclosure tag.
        var imageUrlFromContent: String? = null

        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()
        val reader: Reader = InputStreamReader(ByteArrayInputStream(xml.trim().toByteArray()))

        xmlPullParser.setInput(reader)

        // A flag just to be sure of the correct parsing
        var insideItem = false
        var insideChannel = false
        var insideChannelImage = false

        var eventType = xmlPullParser.eventType

        // Start parsing the xml
        loop@ while (eventType != XmlPullParser.END_DOCUMENT) {

            // Start parsing the item
            when {
                eventType == XmlPullParser.START_TAG -> {
                    when {
                        xmlPullParser.contains(RSSKeyword.Channel.Channel) -> insideChannel = true
                        xmlPullParser.contains(RSSKeyword.Item.Item) -> insideItem = true
                        xmlPullParser.contains(RSSKeyword.Image) -> {
                            if (insideChannel && !insideItem) {
                                insideChannelImage = true
                            } else if (insideItem) {
                                articleBuilder.image(xmlPullParser.nextTrimmedText())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Title) -> {
                            if (insideChannel) {
                                when {
                                    insideChannelImage -> {
                                        channelImageBuilder.title(xmlPullParser.nextTrimmedText())
                                    }
                                    insideItem -> {
                                        articleBuilder.title(xmlPullParser.nextTrimmedText())
                                    }
                                    else -> {
                                        channelBuilder.title(xmlPullParser.nextTrimmedText())
                                    }
                                }
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Link) -> {
                            if (insideChannel) {
                                when {
                                    insideChannelImage -> {
                                        channelImageBuilder.link(xmlPullParser.nextTrimmedText())
                                    }
                                    insideItem -> {
                                        articleBuilder.link(xmlPullParser.nextTrimmedText())
                                    }
                                    else -> {
                                        channelBuilder.link(xmlPullParser.nextTrimmedText())
                                    }
                                }
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Author) -> {
                            if (insideItem) {
                                articleBuilder.author(xmlPullParser.nextTrimmedText())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Category) -> {
                            if (insideItem) {
                                articleBuilder.addCategory(xmlPullParser.nextTrimmedText())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Thumbnail) -> {
                            if (insideItem) {
                                articleBuilder.image(xmlPullParser.attributeValue(RSSKeyword.Item.URL))
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.MediaContent) -> {
                            if (insideItem) {
                                articleBuilder.image(xmlPullParser.attributeValue(RSSKeyword.Item.URL))
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.URL) -> {
                            if (insideChannelImage) {
                                channelImageBuilder.url(xmlPullParser.nextText().trim())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Itunes.Image) -> {
                            if (insideItem) {
                                articleBuilder.image(xmlPullParser.attributeValue(RSSKeyword.Item.HREF))
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Enclosure) -> {
                            if (insideItem) {
                                val type = xmlPullParser.attributeValue(RSSKeyword.Item.Type)
                                if (type != null && type.contains("image")) {
                                    // If there are multiple elements, we take only the first
                                    articleBuilder.imageIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.Item.URL
                                        )
                                    )
                                } else if (type != null && type.contains("audio")) {
                                    // If there are multiple elements, we take only the first
                                    articleBuilder.audioIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.Item.URL
                                        )
                                    )
                                } else if (type != null && type.contains("video")) {
                                    // If there are multiple elements, we take only the first
                                    articleBuilder.videoIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.Item.URL
                                        )
                                    )
                                }
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Source) -> {
                            if (insideItem) {
                                val sourceUrl = xmlPullParser.attributeValue(RSSKeyword.Item.URL)
                                val sourceName = xmlPullParser.nextText()
                                articleBuilder.sourceName(sourceName)
                                articleBuilder.sourceUrl(sourceUrl)
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Description) -> {
                            if (insideChannel) {
                                when {
                                    insideItem -> {
                                        val description = xmlPullParser.nextTrimmedText()
                                        articleBuilder.description(description)
                                        imageUrlFromContent = getImageUrl(description)
                                    }
                                    insideChannelImage -> {
                                        channelImageBuilder.description(xmlPullParser.nextTrimmedText())
                                    }
                                    else -> {
                                        channelBuilder.description(xmlPullParser.nextTrimmedText())
                                    }
                                }
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Content) -> {
                            if (insideItem) {
                                val content = xmlPullParser.nextTrimmedText()
                                articleBuilder.content(content)
                                imageUrlFromContent = getImageUrl(content)
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.PubDate) -> {
                            if (insideItem) {
                                val nextTokenType = xmlPullParser.next()
                                if (nextTokenType == XmlPullParser.TEXT) {
                                    articleBuilder.pubDate(xmlPullParser.text.trim())
                                }
                                // Skip to be able to find date inside 'tag' tag
                                continue@loop
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.Time) -> {
                            if (insideItem) {
                                articleBuilder.pubDate(xmlPullParser.nextTrimmedText())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.GUID) -> {
                            if (insideItem) {
                                articleBuilder.guid(xmlPullParser.nextTrimmedText())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Channel.LastBuildDate) -> {
                            if (insideChannel) {
                                channelBuilder.lastBuildDate(xmlPullParser.nextTrimmedText())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Channel.UpdatePeriod) -> {
                            if (insideChannel) {
                                channelBuilder.updatePeriod(xmlPullParser.nextTrimmedText())
                            }
                        }
                        xmlPullParser.contains(RSSKeyword.Item.News.Image) -> {
                            if (insideItem) {
                                articleBuilder.image(xmlPullParser.nextTrimmedText())
                            }
                        }
                    }
                }
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Item.Item) -> {
                    // The item is correctly parsed
                    insideItem = false
                    articleBuilder.imageIfNull(imageUrlFromContent)
                    imageUrlFromContent = null
                    channelBuilder.addArticle(articleBuilder.build())
                    articleBuilder = Article.Builder()
                }
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Channel.Channel) -> {
                    // The channel is correctly parsed
                    insideChannel = false
                }
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Image) -> {
                    // The channel image is correctly parsed
                    insideChannelImage = false
                }
            }
            eventType = xmlPullParser.next()
        }

        val channelImage = channelImageBuilder.build()
        if (!channelImage.isEmpty()) {
            channelBuilder.image(channelImage)
        }

        return channelBuilder.build()
    }

    /**
     * Finds the first img tag and get the src as featured image
     *
     * @param input The content in which to search for the tag
     * @return The url, if there is one
     */
    private fun getImageUrl(input: String?): String? {
        var url: String? = null
        val patternImg = Pattern.compile("(<img .*?>)")
        val matcherImg = patternImg.matcher(input)
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
