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

package com.prof18.rssparser.internal.rss

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.RssKeyword
import com.prof18.rssparser.internal.attributeValue
import com.prof18.rssparser.internal.contains
import com.prof18.rssparser.internal.nextTrimmedText
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import org.xmlpull.v1.XmlPullParser

internal fun CoroutineScope.extractRSSContent(
    xmlPullParser: XmlPullParser,
): RssChannel {
    val channelFactory = ChannelFactory()

    // A flag just to be sure of the correct parsing
    var insideItem = false
    var insideChannel = false
    var insideChannelImage = false
    var insideItunesOwner = false

    var eventType = xmlPullParser.eventType

    // Start parsing the xml
    loop@ while (eventType != XmlPullParser.END_DOCUMENT && isActive) {
        // Start parsing the item
        when {
            eventType == XmlPullParser.START_TAG -> when {
                // Entering conditions
                xmlPullParser.contains(RssKeyword.CHANNEL) -> {
                    insideChannel = true
                }

                xmlPullParser.contains(RssKeyword.ITEM) -> {
                    insideItem = true
                }

                xmlPullParser.contains(RssKeyword.CHANNEL_ITUNES_OWNER) -> {
                    insideItunesOwner = true
                }

                //region Channel tags
                xmlPullParser.contains(RssKeyword.CHANNEL_LAST_BUILD_DATE) -> {
                    if (insideChannel) {
                        channelFactory.channelBuilder.lastBuildDate(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.CHANNEL_UPDATE_PERIOD) -> {
                    if (insideChannel) {
                        channelFactory.channelBuilder.updatePeriod(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.URL) -> {
                    if (insideChannelImage) {
                        channelFactory.channelImageBuilder.url(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.CHANNEL_ITUNES_CATEGORY) -> {
                    if (insideChannel) {
                        val category = xmlPullParser.attributeValue(RssKeyword.CHANNEL_ITUNES_TEXT)
                        channelFactory.itunesChannelBuilder.addCategory(category)
                    }
                }

                xmlPullParser.contains(RssKeyword.CHANNEL_ITUNES_TYPE) -> {
                    if (insideChannel) {
                        channelFactory.itunesChannelBuilder.type(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.CHANNEL_ITUNES_NEW_FEED_URL) -> {
                    if (insideChannel) {
                        channelFactory.itunesChannelBuilder.newsFeedUrl(xmlPullParser.nextTrimmedText())
                    }
                }
                //endregion

                //region Item tags
                xmlPullParser.contains(RssKeyword.ITEM_AUTHOR) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.author(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_DATE) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.pubDateIfNull(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_CATEGORY) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.addCategory(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_THUMBNAIL) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.image(
                            xmlPullParser.attributeValue(RssKeyword.URL)
                        )
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_MEDIA_CONTENT) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.image(
                            xmlPullParser.attributeValue(
                                RssKeyword.URL
                            )
                        )
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_ENCLOSURE) -> {
                    if (insideItem) {
                        val type = xmlPullParser.attributeValue(RssKeyword.ITEM_TYPE)
                        val url = xmlPullParser.attributeValue(RssKeyword.URL)
                        val length = xmlPullParser.attributeValue(RssKeyword.ITEM_LENGTH)

                        channelFactory.rawEnclosureBuilder.length(length?.toLongOrNull())
                        channelFactory.rawEnclosureBuilder.type(type)
                        channelFactory.rawEnclosureBuilder.url(url)

                        when {
                            type != null && type.contains("image") -> {
                                // If there are multiple elements, we take only the first
                                channelFactory.articleBuilder.image(url)
                            }

                            type != null && type.contains("audio") -> {
                                // If there are multiple elements, we take only the first
                                channelFactory.articleBuilder.audioIfNull(url)
                            }

                            type != null && type.contains("video") -> {
                                // If there are multiple elements, we take only the first
                                channelFactory.articleBuilder.videoIfNull(url)
                            }

                            else -> channelFactory.articleBuilder.image(
                                xmlPullParser.nextText().trim()
                            )
                        }
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_SOURCE) -> {
                    if (insideItem) {
                        val sourceUrl = xmlPullParser.attributeValue(RssKeyword.URL)
                        val sourceName = xmlPullParser.nextText()
                        channelFactory.articleBuilder.sourceName(sourceName)
                        channelFactory.articleBuilder.sourceUrl(sourceUrl)
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_TIME) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.pubDate(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_GUID) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.guid(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_CONTENT) -> {
                    if (insideItem) {
                        val content = xmlPullParser.nextTrimmedText()
                        channelFactory.articleBuilder.content(content)
                        channelFactory.setImageFromContent(content)
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_PUB_DATE) -> {
                    if (insideItem) {
                        val nextTokenType = xmlPullParser.next()
                        if (nextTokenType == XmlPullParser.TEXT) {
                            channelFactory.articleBuilder.pubDate(xmlPullParser.text.trim())
                        }
                        // Skip to be able to find date inside 'tag' tag
                        continue@loop
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_NEWS_IMAGE) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.image(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_ITUNES_EPISODE) -> {
                    if (insideItem) {
                        channelFactory.itunesArticleBuilder.episode(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_ITUNES_EPISODE_TYPE) -> {
                    if (insideItem) {
                        channelFactory.itunesArticleBuilder.episodeType(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_ITUNES_SEASON) -> {
                    if (insideItem) {
                        channelFactory.itunesArticleBuilder.season(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_COMMENTS) -> {
                    if (insideItem) {
                        val url = xmlPullParser.nextTrimmedText()
                        channelFactory.articleBuilder.commentUrl(url)
                    }
                }

                xmlPullParser.contains(RssKeyword.ITEM_THUMB) -> {
                    if (insideItem) {
                        val imageUrl = xmlPullParser.nextTrimmedText()
                        channelFactory.articleBuilder.image(imageUrl)
                    }
                }
                //endregion

                //region Itunes Owner tags
                xmlPullParser.contains(RssKeyword.CHANNEL_ITUNES_OWNER_NAME) -> {
                    if (insideItunesOwner) {
                        channelFactory.itunesOwnerBuilder.name(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RssKeyword.CHANNEL_ITUNES_OWNER_EMAIL) -> {
                    if (insideItunesOwner) {
                        channelFactory.itunesOwnerBuilder.email(xmlPullParser.nextTrimmedText())
                    }
                }
                //endregion

                //region Mixed tags
                xmlPullParser.contains(RssKeyword.IMAGE) -> when {
                    insideChannel && !insideItem -> insideChannelImage = true
                    insideItem -> {
                        xmlPullParser.next()
                        val text = xmlPullParser.text?.trim()
                        // Get the image text if it's not contained in another tag
                        if (!text.isNullOrEmpty()) {
                            channelFactory.articleBuilder.image(text)
                        } else {
                            xmlPullParser.next()
                            if (xmlPullParser.contains(RssKeyword.LINK)) {
                                channelFactory.articleBuilder.image(xmlPullParser.nextTrimmedText())
                            }
                        }
                    }
                }

                xmlPullParser.contains(RssKeyword.TITLE) -> {
                    if (insideChannel) {
                        when {
                            insideChannelImage -> {
                                channelFactory.channelImageBuilder.title(xmlPullParser.nextTrimmedText())
                            }

                            insideItem -> channelFactory.articleBuilder.title(xmlPullParser.nextTrimmedText())
                            else -> channelFactory.channelBuilder.title(xmlPullParser.nextTrimmedText())
                        }
                    }
                }

                xmlPullParser.contains(RssKeyword.LINK) -> {
                    if (insideChannel) {
                        when {
                            insideChannelImage -> {
                                channelFactory.channelImageBuilder.link(xmlPullParser.nextTrimmedText())
                            }

                            insideItem -> channelFactory.articleBuilder.link(xmlPullParser.nextTrimmedText())
                            else -> channelFactory.channelBuilder.link(xmlPullParser.nextTrimmedText())
                        }
                    }
                }

                xmlPullParser.contains(RssKeyword.DESCRIPTION) -> {
                    if (insideChannel) {
                        when {
                            insideItem -> {
                                val description = xmlPullParser.nextTrimmedText()
                                channelFactory.articleBuilder.description(description)
                                channelFactory.setImageFromContent(description)
                            }

                            insideChannelImage -> {
                                channelFactory.channelImageBuilder.description(xmlPullParser.nextTrimmedText())
                            }

                            else -> channelFactory.channelBuilder.description(xmlPullParser.nextTrimmedText())
                        }
                    }
                }

                xmlPullParser.contains(RssKeyword.ITUNES_AUTHOR) -> when {
                    insideItem -> channelFactory.itunesArticleBuilder.author(xmlPullParser.nextTrimmedText())
                    insideChannel -> channelFactory.itunesChannelBuilder.author(xmlPullParser.nextTrimmedText())
                }

                xmlPullParser.contains(RssKeyword.ITUNES_DURATION) -> when {
                    insideItem -> channelFactory.itunesArticleBuilder.duration(xmlPullParser.nextTrimmedText())
                    insideChannel -> channelFactory.itunesChannelBuilder.duration(xmlPullParser.nextTrimmedText())
                }

                xmlPullParser.contains(RssKeyword.ITUNES_KEYWORDS) -> {
                    val keywords = xmlPullParser.nextTrimmedText()
                    when {
                        insideItem -> channelFactory.setArticleItunesKeywords(keywords)
                        insideChannel -> channelFactory.setChannelItunesKeywords(keywords)
                    }
                }

                xmlPullParser.contains(RssKeyword.ITUNES_IMAGE) -> when {
                    insideItem -> channelFactory.itunesArticleBuilder.image(
                        xmlPullParser.attributeValue(
                            RssKeyword.HREF
                        )
                    )

                    insideChannel -> channelFactory.itunesChannelBuilder.image(
                        xmlPullParser.attributeValue(
                            RssKeyword.HREF
                        )
                    )
                }

                xmlPullParser.contains(RssKeyword.ITUNES_EXPLICIT) -> when {
                    insideItem -> channelFactory.itunesArticleBuilder.explicit(xmlPullParser.nextTrimmedText())
                    insideChannel -> channelFactory.itunesChannelBuilder.explicit(xmlPullParser.nextTrimmedText())
                }

                xmlPullParser.contains(RssKeyword.ITUNES_SUBTITLE) -> when {
                    insideItem -> channelFactory.itunesArticleBuilder.subtitle(xmlPullParser.nextTrimmedText())
                    insideChannel -> channelFactory.itunesChannelBuilder.subtitle(xmlPullParser.nextTrimmedText())
                }

                xmlPullParser.contains(RssKeyword.ITUNES_SUMMARY) -> when {
                    insideItem -> channelFactory.itunesArticleBuilder.summary(xmlPullParser.nextTrimmedText())
                    insideChannel -> channelFactory.itunesChannelBuilder.summary(xmlPullParser.nextTrimmedText())
                }
                //endregion
            }

            // Exit conditions
            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RssKeyword.ITEM) -> {
                // The item is correctly parsed
                insideItem = false
                // Set data
                channelFactory.buildArticle()
            }

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RssKeyword.CHANNEL) -> {
                // The channel is correctly parsed
                insideChannel = false
            }

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RssKeyword.IMAGE) -> {
                // The channel image is correctly parsed
                insideChannelImage = false
            }

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RssKeyword.CHANNEL_ITUNES_OWNER) -> {
                // The itunes owner is correctly parsed
                channelFactory.buildItunesOwner()
                insideItunesOwner = false
            }
        }
        eventType = xmlPullParser.next()
    }

    return channelFactory.build()
}
