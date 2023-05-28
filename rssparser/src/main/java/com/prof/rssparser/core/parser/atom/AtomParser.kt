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

package com.prof.rssparser.core.parser.atom

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.Image
import com.prof.rssparser.ItunesArticleData
import com.prof.rssparser.ItunesChannelData
import com.prof.rssparser.core.CoreXMLParser.getImageUrl
import com.prof.rssparser.utils.AtomKeyword
import com.prof.rssparser.utils.attributeValue
import com.prof.rssparser.utils.contains
import com.prof.rssparser.utils.nextTrimmedText
import org.xmlpull.v1.XmlPullParser


internal fun extractAtomContent(xmlPullParser: XmlPullParser): Channel {
    val channelBuilder = Channel.Builder()
    var articleBuilder = Article.Builder()
    val channelImageBuilder = Image.Builder()
    val itunesChannelBuilder = ItunesChannelData.Builder()
    var itunesArticleBuilder = ItunesArticleData.Builder()

    // This image url is extracted from the content and the description of the rss item.
    // It's a fallback just in case there aren't any images in the enclosure tag.
    var imageUrlFromContent: String? = null

    // A flag just to be sure of the correct parsing
    var insideItem = false
    var insideAtom = false

    var eventType = xmlPullParser.eventType

    // Start parsing the xml
    loop@ while (eventType != XmlPullParser.END_DOCUMENT) {

        // Start parsing the item
        when {
            eventType == XmlPullParser.START_TAG -> when {
                // Entering conditions
                xmlPullParser.contains(AtomKeyword.ATOM) -> {
                    insideAtom = true
                }

                xmlPullParser.contains(AtomKeyword.Entry.Item) -> {
                    insideItem = true
                }
                //endregion

                //region Item tags
                xmlPullParser.contains(AtomKeyword.Entry.Author) -> {
                    if (insideItem) {
                        articleBuilder.author(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.Entry.Category) -> {
                    if (insideItem) {
                        articleBuilder.addCategory(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.Entry.GUID) -> {
                    if (insideItem) {
                        articleBuilder.guid(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.Entry.Content) -> {
                    if (insideItem) {
                        try {
                            val content = xmlPullParser.nextTrimmedText()
                            articleBuilder.content(content)
                            imageUrlFromContent = getImageUrl(content)
                        } catch (throwable: Throwable) {
                            // TODO some content tag with the no CDATA html that like this:
                            // <content type="html">
                            //  This post was published externally on Cash App Code Blog. Read it at
                            //  <a href="https://code.cash.app/the-state-of-managing-state-with-compose">https://code.cash.app/the-state-of-managing-state-with-compose</a>
                            //  .
                            // </content>
                            continue@loop
                        }
                    }
                }

                xmlPullParser.contains(AtomKeyword.Entry.PubDate) -> {
                    if (insideItem) {
                        val nextTokenType = xmlPullParser.next()
                        if (nextTokenType == XmlPullParser.TEXT) {
                            articleBuilder.pubDate(xmlPullParser.text.trim())
                        }
                        // Skip to be able to find date inside 'tag' tag
                        continue@loop
                    }
                }

                xmlPullParser.contains(AtomKeyword.Entry.PubDate) -> {
                    if (insideItem) {
                        val nextTokenType = xmlPullParser.next()
                        if (nextTokenType == XmlPullParser.TEXT) {
                            articleBuilder.pubDate(xmlPullParser.text.trim())
                        }
                        // Skip to be able to find date inside 'tag' tag
                        continue@loop
                    }
                }

                xmlPullParser.contains(AtomKeyword.SUBTITLE) -> {
                    if (insideAtom) {
                        channelBuilder.description(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.Entry.Description) -> {
                    if (insideItem) {
                        val description = xmlPullParser.nextTrimmedText()
                        articleBuilder.description(description)
                        imageUrlFromContent = getImageUrl(description)
                    }
                }
                //region Mixed tags
                xmlPullParser.contains(AtomKeyword.Title) -> {
                    if (insideAtom) {
                        when {
                            insideItem -> articleBuilder.title(xmlPullParser.nextTrimmedText())
                            else -> channelBuilder.title(xmlPullParser.nextTrimmedText())
                        }
                    }
                }

                xmlPullParser.contains(AtomKeyword.Link) -> {
                    if (insideAtom) {
                        val href = xmlPullParser.attributeValue(
                            AtomKeyword.Link.HREF
                        )
                        val rel = xmlPullParser.attributeValue(
                            AtomKeyword.Link.REL
                        )
                        //val type = xmlPullParser.attributeValue(
                        //    AtomKeyword.Link.TYPE
                        //)
                        if ("edit".equals(rel, true).not()) {
                            when {
                                insideItem -> articleBuilder.link(href)
                                else -> channelBuilder.link(href)
                            }
                        }
                    }
                }
            }

            // Exit conditions
            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(AtomKeyword.Entry.Item) -> {
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

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(AtomKeyword.ATOM) -> {
                // The channel is correctly parsed
                insideAtom = false
            }
        }
        eventType = xmlPullParser.next()
    }

    val channelImage = channelImageBuilder.build()
    if (channelImage.isNotEmpty()) {
        channelBuilder.image(channelImage)
    }
    channelBuilder.itunesChannelData(itunesChannelBuilder.build())

    return channelBuilder.build()
}
