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

package com.prof18.rssparser.internal.atom

import com.prof18.rssparser.internal.AtomKeyword
import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.ParserInput
import com.prof18.rssparser.internal.attributeValue
import com.prof18.rssparser.internal.contains
import com.prof18.rssparser.internal.nextTrimmedText
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

internal fun CoroutineScope.extractAtomContent(
    xmlPullParser: XmlPullParser,
    input: ParserInput,
): RssChannel {
    val channelFactory = ChannelFactory()

    // A flag just to be sure of the correct parsing
    var insideItem = false
    var insideChannel = false
    var insideYoutubeMediaGroup = false

    var eventType = xmlPullParser.eventType

    // Start parsing the xml
    loop@ while (eventType != XmlPullParser.END_DOCUMENT && isActive) {
        // Start parsing the item
        when {
            eventType == XmlPullParser.START_TAG -> when {
                // Entering conditions
                xmlPullParser.contains(AtomKeyword.ATOM) -> {
                    insideChannel = true
                }

                xmlPullParser.contains(AtomKeyword.ENTRY_ITEM) -> {
                    insideItem = true
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP) -> {
                    if (insideItem) {
                        insideYoutubeMediaGroup = true
                    }
                }
                //endregion

                //region Channel tags
                xmlPullParser.contains(AtomKeyword.ICON) -> {
                    if (insideChannel) {
                        channelFactory.channelImageBuilder.url(xmlPullParser.nextTrimmedText())
                    }
                }
                //endregion

                //region Item tags
                xmlPullParser.contains(AtomKeyword.ENTRY_AUTHOR) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.author(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.ENTRY_CATEGORY) -> {
                    if (insideItem) {
                        val nextText = xmlPullParser.nextTrimmedText()
                        val termAttributeValue = xmlPullParser.attributeValue(AtomKeyword.ENTRY_TERM)

                        /**
                         * We want to look at the 'term' attribute and use that if no text is present
                         * such as `<category term="android"/>`
                         */
                        val categoryText = if (nextText?.isEmpty() == true) {
                            termAttributeValue
                        } else {
                            nextText
                        }
                        channelFactory.articleBuilder.addCategory(categoryText)
                    }
                }

                xmlPullParser.contains(AtomKeyword.ENTRY_GUID) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.guid(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.ENTRY_CONTENT) -> {
                    if (insideItem) {
                        val content = xmlPullParser.nextTrimmedText()
                        channelFactory.articleBuilder.content(content)
                        channelFactory.setImageFromContent(content)
                    }
                }

                xmlPullParser.contains(AtomKeyword.UPDATED) -> {
                    when {
                        insideItem -> {
                            channelFactory.articleBuilder.pubDateIfNull(xmlPullParser.nextTrimmedText())
                        }

                        insideChannel -> {
                            channelFactory.channelBuilder.lastBuildDate(xmlPullParser.nextTrimmedText())
                        }
                    }
                }

                xmlPullParser.contains(AtomKeyword.ENTRY_PUBLISHED) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.pubDateIfNull(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.SUBTITLE) -> {
                    if (insideChannel) {
                        channelFactory.channelBuilder.description(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.ENTRY_DESCRIPTION) -> {
                    if (insideItem) {
                        val description = xmlPullParser.nextTrimmedText()
                        channelFactory.articleBuilder.description(description)
                        channelFactory.setImageFromContent(description)
                    }
                }
                //region Mixed tags
                xmlPullParser.contains(AtomKeyword.TITLE) -> {
                    when {
                        insideItem -> channelFactory.articleBuilder.title(xmlPullParser.nextTrimmedText())
                        insideChannel -> channelFactory.channelBuilder.title(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.LINK) -> {
                    if (insideChannel) {
                        val href = xmlPullParser.attributeValue(
                            AtomKeyword.LINK_HREF
                        )
                        val rel = xmlPullParser.attributeValue(
                            AtomKeyword.LINK_REL
                        )
                        val link = if (input.baseUrl != null &&
                            rel == AtomKeyword.LINK_REL_ALTERNATE.value &&
                            // Some feeds have full links
                            href?.startsWith("/") == true
                        ) {
                            input.baseUrl + href
                        } else {
                            href
                        }
                        if (rel != AtomKeyword.LINK_EDIT.value &&
                            rel != AtomKeyword.LINK_SELF.value &&
                            rel != AtomKeyword.LINK_REL_REPLIES.value &&
                            rel != AtomKeyword.LINK_REL_ENCLOSURE.value
                        ) {
                            when {
                                insideItem -> channelFactory.articleBuilder.link(link, rel)
                                !insideItem -> channelFactory.channelBuilder.link(link)
                            }
                        }
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_CHANNEL_ID) -> {
                    if (insideItem) {
                        channelFactory.youtubeChannelDataBuilder.channelId(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_VIDEO_ID) -> {
                    if (insideItem) {
                        channelFactory.youtubeItemDataBuilder.videoId(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP_TITLE) -> {
                    if (insideItem && insideYoutubeMediaGroup) {
                        channelFactory.youtubeItemDataBuilder.title(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP_DESCRIPTION) -> {
                    if (insideItem && insideYoutubeMediaGroup) {
                        channelFactory.youtubeItemDataBuilder.description(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT) -> {
                    if (insideItem && insideYoutubeMediaGroup) {
                        val videoUrl = xmlPullParser.attributeValue(AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT_URL)
                        channelFactory.youtubeItemDataBuilder.videoUrl(videoUrl)
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL) -> {
                    if (insideItem) {
                        val thumbnailUrl = xmlPullParser.attributeValue(
                            AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL_URL
                        )
                        if (insideYoutubeMediaGroup) {
                            channelFactory.youtubeItemDataBuilder.thumbnailUrl(thumbnailUrl)
                        } else {
                            channelFactory.articleBuilder.image(thumbnailUrl)
                        }
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS) -> {
                    if (insideItem && insideYoutubeMediaGroup) {
                        val views = xmlPullParser.attributeValue(
                            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS_VIEWS
                        )
                        channelFactory.youtubeItemDataBuilder.viewsCount(views?.toIntOrNull())
                    }
                }

                xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING) -> {
                    if (insideItem && insideYoutubeMediaGroup) {
                        val count = xmlPullParser.attributeValue(
                            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING_COUNT
                        )
                        channelFactory.youtubeItemDataBuilder.likesCount(count?.toIntOrNull())
                    }
                }
            }

            // Exit conditions
            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(AtomKeyword.YOUTUBE_MEDIA_GROUP) -> {
                insideYoutubeMediaGroup = false
            }

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(AtomKeyword.ENTRY_ITEM) -> {
                // The item is correctly parsed
                insideItem = false
                insideYoutubeMediaGroup = false
                channelFactory.buildArticle()
            }

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(AtomKeyword.ATOM) -> {
                // The channel is correctly parsed
                insideChannel = false
            }
        }
        eventType = xmlPullParser.next()
    }
    return channelFactory.build()
}
