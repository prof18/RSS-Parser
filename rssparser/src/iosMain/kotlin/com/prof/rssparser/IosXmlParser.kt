package com.prof.rssparser

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import platform.Foundation.NSXMLParser
import platform.Foundation.NSXMLParserDelegateProtocol
import platform.darwin.NSObject

internal class IosXmlParser(
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {

    override suspend fun parseXML(input: ParserInput): Channel = withContext(dispatcher) {
        suspendCoroutine { continuation ->
            NSXMLParser(input.data).apply {
                delegate = NSXMLParserDelegate { continuation.resume(it) }
            }.parse()
        }
    }
}

private class NSXMLParserDelegate(
    private val onEnd: (Channel) -> Unit
) : NSObject(), NSXMLParserDelegateProtocol {

    private var currentElement: String? = null
    private var channelFactory = ChannelFactory()
    private var itemData: MutableMap<String, String> = mutableMapOf()
    private var itunesOwnerData: MutableMap<String, String> = mutableMapOf()
    private var channelImageData: MutableMap<String, String> = mutableMapOf()
    private var channelData: MutableMap<String, String> = mutableMapOf()

    var isInsideItem = false
    var isInsideChannel = false
    var isInsideChannelImage = false
    var isInsideItunesOwner = false

    override fun parser(
        parser: NSXMLParser,
        didStartElement: String,
        namespaceURI: String?,
        qualifiedName: String?,
        attributes: Map<Any?, *>,
    ) {
        currentElement = didStartElement
        when (currentElement) {
            RSSKeyword.Channel.Channel.value -> isInsideChannel = true
            RSSKeyword.Item.Item.value -> isInsideItem = true
            RSSKeyword.Channel.Itunes.Owner.value -> isInsideItunesOwner = true
            RSSKeyword.Image.value -> isInsideChannelImage = isInsideChannel && !isInsideItem
            RSSKeyword.Item.MediaContent.value -> {
                if (isInsideItem) {
                    val url = attributes.getValueOrNull(RSSKeyword.URL.value) as? String
                    channelFactory.articleBuilder.image(url)
                }
            }

            RSSKeyword.Item.Thumbnail.value -> {
                if (isInsideItem) {
                    val url = attributes.getValueOrNull(RSSKeyword.URL.value) as? String
                    channelFactory.articleBuilder.image(url)
                }
            }

            RSSKeyword.Item.Enclosure.value -> {
                if (isInsideItem) {
                    val type = attributes[RSSKeyword.Item.Type.value] as? String

                    when {
                        type != null && type.contains("image") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.image(
                                attributes.getValueOrNull(RSSKeyword.URL.value) as? String
                            )
                        }

                        type != null && type.contains("audio") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.audioIfNull(
                                attributes.getValueOrNull(RSSKeyword.URL.value) as? String
                            )
                        }

                        type != null && type.contains("video") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.videoIfNull(
                                attributes.getValueOrNull(RSSKeyword.URL.value) as? String
                            )
                        }
                    }
                }
            }

            RSSKeyword.Itunes.Image.value -> {
                val url = attributes.getValueOrNull(RSSKeyword.HREF.value) as? String
                when {
                    isInsideItem -> channelFactory.itunesArticleBuilder.image(url)
                    isInsideChannel -> channelFactory.itunesChannelBuilder.image(url)
                }
            }

            RSSKeyword.Item.Source.value -> {
                if (isInsideItem) {
                    val sourceUrl = attributes.getValueOrNull(RSSKeyword.URL.value) as? String
                    channelFactory.articleBuilder.sourceUrl(sourceUrl)
                }
            }

            RSSKeyword.Channel.Itunes.Category.value -> {
                if (isInsideChannel) {
                    val category = attributes.getValueOrNull(RSSKeyword.Channel.Itunes.Text.value) as? String
                    channelFactory.itunesChannelBuilder.addCategory(category)
                }
            }
        }
    }

    override fun parser(parser: NSXMLParser, foundCharacters: String) {
        val element = currentElement ?: return

        when {
            isInsideItem && element == RSSKeyword.Item.Category.value -> {
                val category = foundCharacters.trim()
                if (category.isNotEmpty()) {
                    channelFactory.articleBuilder.addCategory(category)
                }
            }

            isInsideItem -> itemData[element] = (itemData[element].orEmpty()) + foundCharacters
            isInsideItunesOwner -> itunesOwnerData[element] = (itunesOwnerData[element].orEmpty()) + foundCharacters
            isInsideChannelImage -> channelImageData[element] = (channelImageData[element].orEmpty()) + foundCharacters
            isInsideChannel -> channelData[element] = (channelData[element].orEmpty()) + foundCharacters
        }
    }

    override fun parser(
        parser: NSXMLParser,
        didEndElement: String,
        namespaceURI: String?,
        qualifiedName: String?
    ) {
        when (didEndElement) {

            RSSKeyword.Channel.Channel.value -> {
                channelFactory.channelBuilder.title(
                    channelData[RSSKeyword.Title.value]?.trim()
                )
                channelFactory.channelBuilder.link(
                    channelData[RSSKeyword.Link.value]?.trim()
                )
                channelFactory.channelBuilder.description(
                    channelData[RSSKeyword.Description.value]?.trim()
                )
                channelFactory.channelBuilder.lastBuildDate(
                    channelData[RSSKeyword.Channel.LastBuildDate.value]?.trim()
                )
                channelFactory.channelBuilder.updatePeriod(
                    channelData[RSSKeyword.Channel.UpdatePeriod.value]?.trim()
                )

                // Itunes Data
                channelFactory.itunesChannelBuilder.type(
                    channelData[RSSKeyword.Channel.Itunes.Type.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.explicit(
                    channelData[RSSKeyword.Itunes.Explicit.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.subtitle(
                    channelData[RSSKeyword.Itunes.Subtitle.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.summary(
                    channelData[RSSKeyword.Itunes.Summary.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.author(
                    channelData[RSSKeyword.Itunes.Author.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.duration(
                    channelData[RSSKeyword.Itunes.Duration.value]?.trim()
                )
                channelFactory.setChannelItunesKeywords(
                    channelData[RSSKeyword.Itunes.Keywords.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.newsFeedUrl(
                    channelData[RSSKeyword.Channel.Itunes.NewFeedUrl.value]?.trim()
                )

                isInsideChannel = false
            }

            RSSKeyword.Item.Item.value -> {
                channelFactory.articleBuilder.author(
                    itemData[RSSKeyword.Item.Author.value]?.trim()
                )
                channelFactory.articleBuilder.sourceName(
                    itemData[RSSKeyword.Item.Source.value]?.trim()
                )

                val time = itemData[RSSKeyword.Item.Time.value]?.trim()
                if (time != null) {
                    channelFactory.articleBuilder.pubDate(time)
                } else {
                    channelFactory.articleBuilder.pubDate(
                        itemData[RSSKeyword.Item.PubDate.value]?.trim()
                    )
                }

                channelFactory.articleBuilder.guid(
                    itemData[RSSKeyword.Item.GUID.value]?.trim()
                )
                itemData[RSSKeyword.Item.Content.value]?.trim()?.let { content ->
                    channelFactory.setImageFromContent(content)
                    channelFactory.articleBuilder.content(content)
                }
                channelFactory.articleBuilder.image(
                    itemData[RSSKeyword.Item.News.Image.value]?.trim()
                )
                channelFactory.articleBuilder.commentUrl(
                    itemData[RSSKeyword.Item.Comments.value]?.trim()
                )
                channelFactory.articleBuilder.image(
                    itemData[RSSKeyword.Image.value]?.trim()
                )
                channelFactory.articleBuilder.title(
                    itemData[RSSKeyword.Title.value]?.trim()
                )
                channelFactory.articleBuilder.link(
                    itemData[RSSKeyword.Link.value]?.trim()
                )
                itemData[RSSKeyword.Description.value]?.trim()?.let { description ->
                    channelFactory.setImageFromContent(description)
                    channelFactory.articleBuilder.description(description)
                }

                channelFactory.articleBuilder.image(
                    itemData[RSSKeyword.Item.Enclosure.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.episode(
                    itemData[RSSKeyword.Item.Itunes.Episode.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.episodeType(
                    itemData[RSSKeyword.Item.Itunes.EpisodeType.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.season(
                    itemData[RSSKeyword.Item.Itunes.Season.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.explicit(
                    itemData[RSSKeyword.Itunes.Explicit.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.subtitle(
                    itemData[RSSKeyword.Itunes.Subtitle.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.summary(
                    itemData[RSSKeyword.Itunes.Summary.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.author(
                    itemData[RSSKeyword.Itunes.Author.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.duration(
                    itemData[RSSKeyword.Itunes.Duration.value]?.trim()
                )
                channelFactory.setArticleItunesKeywords(
                    itemData[RSSKeyword.Itunes.Keywords.value]?.trim()
                )
                channelFactory.buildArticle()
                itemData.clear()
            }

            RSSKeyword.Channel.Itunes.Owner.value -> {
                channelFactory.itunesOwnerBuilder.name(
                    itunesOwnerData[RSSKeyword.Channel.Itunes.OwnerName.value]?.trim()
                )
                channelFactory.itunesOwnerBuilder.email(
                    itunesOwnerData[RSSKeyword.Channel.Itunes.OwnerEmail.value]?.trim()
                )
                channelFactory.buildItunesOwner()
                isInsideItunesOwner = false
            }

            RSSKeyword.Image.value -> {
                channelFactory.channelImageBuilder.url(
                    channelImageData[RSSKeyword.URL.value]?.trim()
                )
                channelFactory.channelImageBuilder.title(
                    channelImageData[RSSKeyword.Title.value]?.trim()
                )
                channelFactory.channelImageBuilder.link(
                    channelImageData[RSSKeyword.Link.value]?.trim()
                )
                channelFactory.channelImageBuilder.description(
                    channelImageData[RSSKeyword.Description.value]?.trim()
                )
                isInsideChannelImage = false
            }
        }
    }

    override fun parserDidEndDocument(parser: NSXMLParser) {
        onEnd(channelFactory.build())
    }
}
