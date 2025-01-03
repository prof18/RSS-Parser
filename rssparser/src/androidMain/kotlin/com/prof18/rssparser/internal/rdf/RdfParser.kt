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
                xmlPullParser.contains(RdfKeyword.Channel) -> {
                    insideChannel = true
                }

                xmlPullParser.contains(RdfKeyword.Item) -> {
                    insideItem = true
                }

                xmlPullParser.contains(RdfKeyword.Title) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.title(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.title(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.Link) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.link(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.link(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.Description) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.description(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.description(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.DcDate) -> {
                    when {
                        insideChannel -> channelFactory.channelBuilder.lastBuildDate(xmlPullParser.nextTrimmedText())
                        insideItem -> channelFactory.articleBuilder.pubDate(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.Item.DcSubject) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.addCategory(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.Item.DcCreator) -> {
                    if (insideItem) {
                        channelFactory.articleBuilder.author(xmlPullParser.nextTrimmedText())
                    }
                }

                xmlPullParser.contains(RdfKeyword.Channel.Image) -> {
                    val url = xmlPullParser.getAttributeValue(null, RdfKeyword.Channel.Image.Resource.value)
                    channelFactory.channelImageBuilder.url(url)
                }

                xmlPullParser.contains(RdfKeyword.Channel.UpdatePeriod) -> {
                    if (insideChannel) {
                        channelFactory.channelBuilder.updatePeriod(xmlPullParser.nextTrimmedText())
                    }
                }
            }

            // Parsing conditions

            // Exit conditions
            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RdfKeyword.Item) -> {
                // The item is correctly parsed
                insideItem = false
                channelFactory.buildArticle()
            }

            eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RdfKeyword.Channel) -> {
                // The channel is correctly parsed
                insideChannel = false
            }
        }

        eventType = xmlPullParser.next()
    }

    return channelFactory.build()
}
