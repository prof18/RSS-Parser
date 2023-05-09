package com.prof.rssparser.internal

import com.prof.rssparser.model.Channel
import okhttp3.internal.closeQuietly
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.nio.charset.Charset
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

internal class AndroidXmlParser(
    private val charset: Charset? = null,
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {

    override suspend fun parseXML(input: ParserInput): Channel = withContext(dispatcher) {
        val channelFactory = ChannelFactory()

        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()

        // If the charset is null, then the parser will infer it from the feed
        xmlPullParser.setInput(input.inputStream, charset?.toString())

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
                            channelFactory.channelBuilder.lastBuildDate(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Channel.UpdatePeriod) -> {
                        if (insideChannel) {
                            channelFactory.channelBuilder.updatePeriod(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.URL) -> {
                        if (insideChannelImage) {
                            channelFactory.channelImageBuilder.url(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.Category) -> {
                        if (insideChannel) {
                            val category = xmlPullParser.attributeValue(RSSKeyword.Channel.Itunes.Text)
                            channelFactory.itunesChannelBuilder.addCategory(category)
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.Type) -> {
                        if (insideChannel) {
                            channelFactory.itunesChannelBuilder.type(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.NewFeedUrl) -> {
                        if (insideChannel) {
                            channelFactory.itunesChannelBuilder.newsFeedUrl(xmlPullParser.nextTrimmedText())
                        }
                    }
                    //endregion

                    //region Item tags
                    xmlPullParser.contains(RSSKeyword.Item.Author) -> {
                        if (insideItem) {
                            channelFactory.articleBuilder.author(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Category) -> {
                        if (insideItem) {
                            channelFactory.articleBuilder.addCategory(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Thumbnail) -> {
                        if (insideItem) {
                            channelFactory.articleBuilder.image(xmlPullParser.attributeValue(
                                RSSKeyword.URL))
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.MediaContent) -> {
                        if (insideItem) {
                            channelFactory.articleBuilder.image(xmlPullParser.attributeValue(
                                RSSKeyword.URL))
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Enclosure) -> {
                        if (insideItem) {
                            val type = xmlPullParser.attributeValue(RSSKeyword.Item.Type)
                            when {
                                type != null && type.contains("image") -> {
                                    // If there are multiple elements, we take only the first
                                    channelFactory.articleBuilder.image(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.URL
                                        )
                                    )
                                }

                                type != null && type.contains("audio") -> {
                                    // If there are multiple elements, we take only the first
                                    channelFactory.articleBuilder.audioIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.URL
                                        )
                                    )
                                }

                                type != null && type.contains("video") -> {
                                    // If there are multiple elements, we take only the first
                                    channelFactory.articleBuilder.videoIfNull(
                                        xmlPullParser.attributeValue(
                                            RSSKeyword.URL
                                        )
                                    )
                                }

                                else -> channelFactory.articleBuilder.image(
                                    xmlPullParser.nextText().trim()
                                )
                            }
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Source) -> {
                        if (insideItem) {
                            val sourceUrl = xmlPullParser.attributeValue(RSSKeyword.URL)
                            val sourceName = xmlPullParser.nextText()
                            channelFactory.articleBuilder.sourceName(sourceName)
                            channelFactory.articleBuilder.sourceUrl(sourceUrl)
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Time) -> {
                        if (insideItem) {
                            channelFactory.articleBuilder.pubDate(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.GUID) -> {
                        if (insideItem) {
                            channelFactory.articleBuilder.guid(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Content) -> {
                        if (insideItem) {
                            val content = xmlPullParser.nextTrimmedText()
                            channelFactory.articleBuilder.content(content)
                            channelFactory.setImageFromContent(content)
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.PubDate) -> {
                        if (insideItem) {
                            val nextTokenType = xmlPullParser.next()
                            if (nextTokenType == XmlPullParser.TEXT) {
                                channelFactory.articleBuilder.pubDate(xmlPullParser.text.trim())
                            }
                            // Skip to be able to find date inside 'tag' tag
                            continue@loop
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.News.Image) -> {
                        if (insideItem) {
                            channelFactory.articleBuilder.image(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Itunes.Episode) -> {
                        if (insideItem) {
                            channelFactory.itunesArticleBuilder.episode(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Itunes.EpisodeType) -> {
                        if (insideItem) {
                            channelFactory.itunesArticleBuilder.episodeType(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Itunes.Season) -> {
                        if (insideItem) {
                            channelFactory.itunesArticleBuilder.season(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Item.Comments) -> {
                        if (insideItem) {
                            val url = xmlPullParser.nextTrimmedText()
                            channelFactory.articleBuilder.commentUrl(url)
                        }
                    }
                    //endregion

                    //region Itunes Owner tags
                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.OwnerName) -> {
                        if (insideItunesOwner) {
                            channelFactory.itunesOwnerBuilder.name(xmlPullParser.nextTrimmedText())
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Channel.Itunes.OwnerEmail) -> {
                        if (insideItunesOwner) {
                            channelFactory.itunesOwnerBuilder.email(xmlPullParser.nextTrimmedText())
                        }
                    }
                    //endregion

                    //region Mixed tags
                    xmlPullParser.contains(RSSKeyword.Image) -> when {
                        insideChannel && !insideItem -> insideChannelImage = true
                        insideItem -> channelFactory.articleBuilder.image(xmlPullParser.nextTrimmedText())
                    }

                    xmlPullParser.contains(RSSKeyword.Title) -> {
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

                    xmlPullParser.contains(RSSKeyword.Link) -> {
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

                    xmlPullParser.contains(RSSKeyword.Description) -> {
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

                    xmlPullParser.contains(RSSKeyword.Itunes.Author) -> when {
                        insideItem -> channelFactory.itunesArticleBuilder.author(xmlPullParser.nextTrimmedText())
                        insideChannel -> channelFactory.itunesChannelBuilder.author(xmlPullParser.nextTrimmedText())
                    }

                    xmlPullParser.contains(RSSKeyword.Itunes.Duration) -> when {
                        insideItem -> channelFactory.itunesArticleBuilder.duration(xmlPullParser.nextTrimmedText())
                        insideChannel -> channelFactory.itunesChannelBuilder.duration(xmlPullParser.nextTrimmedText())
                    }

                    xmlPullParser.contains(RSSKeyword.Itunes.Keywords) -> {
                        val keywords = xmlPullParser.nextTrimmedText()
                        when {
                            insideItem -> channelFactory.setArticleItunesKeywords(keywords)
                            insideChannel -> channelFactory.setChannelItunesKeywords(keywords)
                        }
                    }

                    xmlPullParser.contains(RSSKeyword.Itunes.Image) -> when {
                        insideItem -> channelFactory.itunesArticleBuilder.image(
                            xmlPullParser.attributeValue(
                                RSSKeyword.HREF
                            )
                        )

                        insideChannel -> channelFactory.itunesChannelBuilder.image(
                            xmlPullParser.attributeValue(
                                RSSKeyword.HREF
                            )
                        )
                    }

                    xmlPullParser.contains(RSSKeyword.Itunes.Explicit) -> when {
                        insideItem -> channelFactory.itunesArticleBuilder.explicit(xmlPullParser.nextTrimmedText())
                        insideChannel -> channelFactory.itunesChannelBuilder.explicit(xmlPullParser.nextTrimmedText())
                    }

                    xmlPullParser.contains(RSSKeyword.Itunes.Subtitle) -> when {
                        insideItem -> channelFactory.itunesArticleBuilder.subtitle(xmlPullParser.nextTrimmedText())
                        insideChannel -> channelFactory.itunesChannelBuilder.subtitle(xmlPullParser.nextTrimmedText())
                    }

                    xmlPullParser.contains(RSSKeyword.Itunes.Summary) -> when {
                        insideItem -> channelFactory.itunesArticleBuilder.summary(xmlPullParser.nextTrimmedText())
                        insideChannel -> channelFactory.itunesChannelBuilder.summary(xmlPullParser.nextTrimmedText())
                    }
                    //endregion
                }

                // Exit conditions
                eventType == XmlPullParser.END_TAG && xmlPullParser.contains(RSSKeyword.Item.Item) -> {
                    // The item is correctly parsed
                    insideItem = false
                    // Set data
                    channelFactory.buildArticle()
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
                    channelFactory.buildItunesOwner()
                    insideItunesOwner = false
                }
            }
            eventType = xmlPullParser.next()
        }

        val channel = channelFactory.build()
        input.inputStream.closeQuietly()
        return@withContext channel
    }
}