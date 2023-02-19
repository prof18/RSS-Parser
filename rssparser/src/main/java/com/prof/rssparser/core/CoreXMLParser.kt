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
import com.prof.rssparser.ItunesArticleData
import com.prof.rssparser.ItunesChannelData
import com.prof.rssparser.ItunesOwner
import com.prof.rssparser.utils.RSSKeyword
import com.prof.rssparser.utils.attributeValue
import com.prof.rssparser.utils.contains
import com.prof.rssparser.utils.nextTrimmedText
import okhttp3.internal.closeQuietly
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.nio.charset.Charset
import java.util.regex.Pattern

internal object CoreXMLParser {

    fun parseXML(inputStream: InputStream, charset: Charset? = null): Channel {

        var articleBuilder = Article.Builder()
        val channelImageBuilder = Image.Builder()
        val channelBuilder = Channel.Builder()
        val itunesChannelBuilder = ItunesChannelData.Builder()
        var itunesArticleBuilder = ItunesArticleData.Builder()
        var itunesOwnerBuilder = ItunesOwner.Builder()

        // This image url is extracted from the content and the description of the rss item.
        // It's a fallback just in case there aren't any images in the enclosure tag.
        var imageUrlFromContent: String? = null

        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()

        // If the charset is null, then the parser will infer it from the feed
        xmlPullParser.setInput(inputStream, charset?.toString())

        // A flag just to be sure of the correct parsing
        var insideItem = false
        var insideChannel = false
        var insideChannelImage = false
        var insideItunesOwner = false

        var eventType = xmlPullParser.eventType

        // Start parsing the xml
        loop@ while (eventType != XmlPullParser.END_DOCUMENT) {

            // Start parsing the item
            when {
                eventType == XmlPullParser.START_TAG -> when {
                    // Entering conditions
                    xmlPullParser.contains(RSSKeyword.Channel.Channel) -> {
                        insideChannel = true
                    }
                    xmlPullParser.contains(RSSKeyword.Item.Item) -> {
                        insideItem = true
                    }
                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.Owner) -> {
                        insideItunesOwner = true
                    }

                    //region Channel tags
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
                    xmlPullParser.contains(RSSKeyword.URL) -> {
                        if (insideChannelImage) {
                            channelImageBuilder.url(xmlPullParser.nextTrimmedText())
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.Category) -> {
                        if (insideChannel) {
                            val category = xmlPullParser.attributeValue(RSSKeyword.Channel.Itunes.Text)
                            itunesChannelBuilder.addCategory(category)
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.Type) -> {
                        if (insideChannel) {
                            itunesChannelBuilder.type(xmlPullParser.nextTrimmedText())
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.NewFeedUrl) -> {
                        if (insideChannel) {
                            itunesChannelBuilder.newsFeedUrl(xmlPullParser.nextTrimmedText())
                        }
                    }
                    //endregion

                    //region Item tags
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
                            articleBuilder.image(xmlPullParser.attributeValue(RSSKeyword.URL))
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Item.MediaContent) -> {
                        if (insideItem) {
                            articleBuilder.image(xmlPullParser.attributeValue(RSSKeyword.URL))
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Item.Enclosure) -> {
                        if (insideItem) {
                            val type = xmlPullParser.attributeValue(RSSKeyword.Item.Type)
                            when {
                                type != null && type.contains("image") -> {
                                    // If there are multiple elements, we take only the first
                                    articleBuilder.imageIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.URL
                                        )
                                    )
                                }
                                type != null && type.contains("audio") -> {
                                    // If there are multiple elements, we take only the first
                                    articleBuilder.audioIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.URL
                                        )
                                    )
                                }
                                type != null && type.contains("video") -> {
                                    // If there are multiple elements, we take only the first
                                    articleBuilder.videoIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.URL
                                        )
                                    )
                                }
                                else -> {
                                    articleBuilder.imageIfNull(
                                        xmlPullParser.nextText().trim()
                                    )
                                }
                            }
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Item.Source) -> {
                        if (insideItem) {
                            val sourceUrl = xmlPullParser.attributeValue(RSSKeyword.URL)
                            val sourceName = xmlPullParser.nextText()
                            articleBuilder.sourceName(sourceName)
                            articleBuilder.sourceUrl(sourceUrl)
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
                    xmlPullParser.contains(RSSKeyword.Item.News.Image) -> {
                        if (insideItem) {
                            articleBuilder.image(xmlPullParser.nextTrimmedText())
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Item.Itunes.Episode) -> {
                        if (insideItem) {
                            itunesArticleBuilder.episode(xmlPullParser.nextTrimmedText())
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Item.Itunes.EpisodeType) -> {
                        if (insideItem) {
                            itunesArticleBuilder.episodeType(xmlPullParser.nextTrimmedText())
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Item.Itunes.Season) -> {
                        if (insideItem) {
                            itunesArticleBuilder.season(xmlPullParser.nextTrimmedText())
                        }
                    }
                    //endregion

                    //region Itunes Owner tags
                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.OwnerName) -> {
                        if (insideItunesOwner) {
                            itunesOwnerBuilder.name(xmlPullParser.nextTrimmedText())
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.OwnerEmail) -> {
                        if (insideItunesOwner) {
                            itunesOwnerBuilder.email(xmlPullParser.nextTrimmedText())
                        }
                    }
                    //endregion

                    //region Mixed tags
                    xmlPullParser.contains(RSSKeyword.Image) -> when {
                        insideChannel && !insideItem -> insideChannelImage = true
                        insideItem -> articleBuilder.image(xmlPullParser.nextTrimmedText())
                    }
                    xmlPullParser.contains(RSSKeyword.Title) -> {
                        if (insideChannel) {
                            when {
                                insideChannelImage -> channelImageBuilder.title(xmlPullParser.nextTrimmedText())
                                insideItem -> articleBuilder.title(xmlPullParser.nextTrimmedText())
                                else -> channelBuilder.title(xmlPullParser.nextTrimmedText())
                            }
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Link) -> {
                        if (insideChannel) {
                            when {
                                insideChannelImage -> channelImageBuilder.link(xmlPullParser.nextTrimmedText())
                                insideItem -> articleBuilder.link(xmlPullParser.nextTrimmedText())
                                else -> channelBuilder.link(xmlPullParser.nextTrimmedText())
                            }
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
                                insideChannelImage -> channelImageBuilder.description(xmlPullParser.nextTrimmedText())
                                else -> channelBuilder.description(xmlPullParser.nextTrimmedText())
                            }
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Itunes.Author) -> when {
                        insideItem -> itunesArticleBuilder.author(xmlPullParser.nextTrimmedText())
                        insideChannel -> itunesChannelBuilder.author(xmlPullParser.nextTrimmedText())
                    }
                    xmlPullParser.contains(RSSKeyword.Itunes.Duration) -> when {
                        insideItem -> itunesArticleBuilder.duration(xmlPullParser.nextTrimmedText())
                        insideChannel -> itunesChannelBuilder.duration(xmlPullParser.nextTrimmedText())
                    }
                    xmlPullParser.contains(RSSKeyword.Itunes.Keywords) -> {
                        val keywords = xmlPullParser.nextTrimmedText()
                        val keywordList = keywords?.split(",")?.mapNotNull {
                            it.ifEmpty {
                                null
                            }
                        } ?: emptyList()
                        if (keywordList.isNotEmpty()) {
                            when {
                                insideItem -> itunesArticleBuilder.keywords(keywordList)
                                insideChannel -> itunesChannelBuilder.keywords(keywordList)
                            }
                        }
                    }
                    xmlPullParser.contains(RSSKeyword.Itunes.Image) -> when {
                        insideItem -> itunesArticleBuilder.image(
                            xmlPullParser.attributeValue(
                                RSSKeyword.HREF
                            )
                        )
                        insideChannel -> itunesChannelBuilder.image(
                            xmlPullParser.attributeValue(
                                RSSKeyword.HREF
                            )
                        )
                    }
                    xmlPullParser.contains(RSSKeyword.Itunes.Explicit) -> when {
                        insideItem -> itunesArticleBuilder.explicit(xmlPullParser.nextTrimmedText())
                        insideChannel -> itunesChannelBuilder.explicit(xmlPullParser.nextTrimmedText())
                    }
                    xmlPullParser.contains(RSSKeyword.Itunes.Subtitle) -> when {
                        insideItem -> itunesArticleBuilder.subtitle(xmlPullParser.nextTrimmedText())
                        insideChannel -> itunesChannelBuilder.subtitle(xmlPullParser.nextTrimmedText())
                    }
                    xmlPullParser.contains(RSSKeyword.Itunes.Summary) -> when {
                        insideItem -> itunesArticleBuilder.summary(xmlPullParser.nextTrimmedText())
                        insideChannel -> itunesChannelBuilder.summary(xmlPullParser.nextTrimmedText())
                    }
                    //endregion
                }

                // Exit conditions
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Item.Item) -> {
                    // The item is correctly parsed
                    insideItem = false
                    // Set data
                    articleBuilder.imageIfNull(imageUrlFromContent)
                    articleBuilder.itunesArticleData(itunesArticleBuilder.build())
                    channelBuilder.addArticle(articleBuilder.build())
                    // Reset temp data
                    imageUrlFromContent = null
                    articleBuilder = Article.Builder()
                    itunesArticleBuilder = ItunesArticleData.Builder()
                }
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Channel.Channel) -> {
                    // The channel is correctly parsed
                    insideChannel = false
                }
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Image) -> {
                    // The channel image is correctly parsed
                    insideChannelImage = false
                }
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Channel.Itunes.Owner) -> {
                    // The itunes owner is correctly parsed
                    itunesChannelBuilder.owner(itunesOwnerBuilder.build())
                    itunesOwnerBuilder = ItunesOwner.Builder()
                    insideItunesOwner = false
                }
            }
            eventType = xmlPullParser.next()
        }

        val channelImage = channelImageBuilder.build()
        if (channelImage.isNotEmpty()) {
            channelBuilder.image(channelImage)
        }
        channelBuilder.itunesChannelData(itunesChannelBuilder.build())

        inputStream.closeQuietly()
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
