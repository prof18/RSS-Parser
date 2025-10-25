package com.prof18.rssparser.internal.rdf

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.RdfKeyword
import com.prof18.rssparser.internal.contains
import com.prof18.rssparser.internal.nextTrimmedText
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import org.xmlpull.v1.XmlPullParser

internal fun CoroutineScope.extractRdfContent(
    xmlPullParser: XmlPullParser
): RssChannel {
    val channelFactory = ChannelFactory()

    var insideItem = false
    var insideChannel = false

    var eventType = xmlPullParser.eventType

    loop@ while (eventType != XmlPullParser.END_DOCUMENT && isActive) {
        when {
            // Entering conditions
            eventType == XmlPullParser.START_TAG -> when {
                xmlPullParser.contains(RdfKeyword.CHANNEL) -> {
                    insideChannel = true
                }

                xmlPullParser.contains(RdfKeyword.ITEM) -> {
                    insideItem = true
                }

                xmlPullParser.contains(RdfKeyword.TITLE) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.title(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.title(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.LINK) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.link(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.link(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.DESCRIPTION) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.description(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.description(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.DC_DATE) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.lastBuildDate(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.pubDate(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.ITEM_DC_SUBJECT) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.addCategory(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.ITEM_DC_CREATOR) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.author(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.CHANNEL_IMAGE) -> {
                    val url = xmlPullParser.getAttributeValue(null, RdfKeyword.CHANNEL_IMAGE_RESOURCE.value)
                    channelFactory.channelImageBuilder.url(url)
                }

                xmlPullParser.contains(RdfKeyword.CHANNEL_UPDATE_PERIOD) -> {
                    if (insideChannel) {
                        channelFactory.channelBuilder.updatePeriod(xmlPullParser.nextTrimmedText())
                    }
                }
            }

            // Parsing conditions

            // Exit conditions
            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RdfKeyword.ITEM) -> {
                // The item is correctly parsed
                insideItem = false
                channelFactory.buildArticle()
            }

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RdfKeyword.CHANNEL) -> {
                // The channel is correctly parsed
                insideChannel = false
            }
        }

        eventType = xmlPullParser.next()
    }

    return channelFactory.build()
}
