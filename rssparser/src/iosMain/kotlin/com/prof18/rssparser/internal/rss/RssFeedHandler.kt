package com.prof18.rssparser.internal.rss

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.RssKeyword
import com.prof18.rssparser.internal.getValueOrNull
import com.prof18.rssparser.model.RssChannel

internal class RssFeedHandler : FeedHandler {

    private var currentElement: String? = null

    private var channelFactory = ChannelFactory()
    private var itemData: MutableMap<String, String> = mutableMapOf()
    private var itunesOwnerData: MutableMap<String, String> = mutableMapOf()
    private var channelImageData: MutableMap<String, String> = mutableMapOf()
    private var channelData: MutableMap<String, String> = mutableMapOf()

    private var isInsideItem = false
    private var isInsideChannel = true
    private var isInsideChannelImage = false
    private var isInsideItunesOwner = false
    private var isInsideItemImage = false

    override fun didStartElement(startElement: String, attributes: Map<Any?, *>) {
        currentElement = startElement

        when (currentElement) {
            RssKeyword.Channel.Channel.value -> isInsideChannel = true
            RssKeyword.Item.Item.value -> isInsideItem = true
            RssKeyword.Channel.Itunes.Owner.value -> isInsideItunesOwner = true
            RssKeyword.Image.value -> {
                when {
                    isInsideItem -> {
                        isInsideItemImage = true
                    }

                    isInsideChannel -> isInsideChannelImage = true
                }
            }

            RssKeyword.Item.MediaContent.value -> {
                if (isInsideItem) {
                    val url = attributes.getValueOrNull(RssKeyword.Url.value) as? String
                    channelFactory.articleBuilder.image(url)
                }
            }

            RssKeyword.Item.Thumbnail.value -> {
                if (isInsideItem) {
                    val url = attributes.getValueOrNull(RssKeyword.Url.value) as? String
                    channelFactory.articleBuilder.image(url)
                }
            }

            RssKeyword.Item.Enclosure.value -> {
                if (isInsideItem) {
                    val type = attributes[RssKeyword.Item.Type.value] as? String

                    when {
                        type != null && type.contains("image") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.image(
                                attributes.getValueOrNull(RssKeyword.Url.value) as? String
                            )
                        }

                        type != null && type.contains("audio") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.audioIfNull(
                                attributes.getValueOrNull(RssKeyword.Url.value) as? String
                            )
                        }

                        type != null && type.contains("video") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.videoIfNull(
                                attributes.getValueOrNull(RssKeyword.Url.value) as? String
                            )
                        }
                    }
                }
            }

            RssKeyword.Itunes.Image.value -> {
                val url = attributes.getValueOrNull(RssKeyword.Href.value) as? String
                when {
                    isInsideItem -> channelFactory.itunesArticleBuilder.image(url)
                    isInsideChannel -> channelFactory.itunesChannelBuilder.image(url)
                }
            }

            RssKeyword.Item.Source.value -> {
                if (isInsideItem) {
                    val sourceUrl = attributes.getValueOrNull(RssKeyword.Url.value) as? String
                    channelFactory.articleBuilder.sourceUrl(sourceUrl)
                }
            }

            RssKeyword.Channel.Itunes.Category.value -> {
                if (isInsideChannel) {
                    val category =
                        attributes.getValueOrNull(RssKeyword.Channel.Itunes.Text.value) as? String
                    channelFactory.itunesChannelBuilder.addCategory(category)
                }
            }
        }
    }

    override fun foundCharacters(characters: String) {
        val element = currentElement ?: return

        when {
            isInsideItemImage -> {
                channelFactory.articleBuilder.image(characters.trim())
            }

            isInsideItem && element == RssKeyword.Item.Category.value -> {
                val category = characters.trim()
                if (category.isNotEmpty()) {
                    channelFactory.articleBuilder.addCategory(category)
                }
            }

            isInsideItem -> {
                itemData[element] = (itemData[element].orEmpty()) + characters
            }
            isInsideItunesOwner -> {
                itunesOwnerData[element] = (itunesOwnerData[element].orEmpty()) + characters
            }
            isInsideChannelImage -> {
                channelImageData[element] = (channelImageData[element].orEmpty()) + characters
            }
            isInsideChannel -> {
                channelData[element] = (channelData[element].orEmpty()) + characters
            }
        }
    }

    override fun didEndElement(endElement: String) {
        when (endElement) {
            RssKeyword.Channel.Channel.value -> {
                channelFactory.channelBuilder.title(
                    channelData[RssKeyword.Title.value]?.trim()
                )
                channelFactory.channelBuilder.link(
                    channelData[RssKeyword.Link.value]?.trim()
                )
                channelFactory.channelBuilder.description(
                    channelData[RssKeyword.Description.value]?.trim()
                )
                channelFactory.channelBuilder.lastBuildDate(
                    channelData[RssKeyword.Channel.LastBuildDate.value]?.trim()
                )
                channelFactory.channelBuilder.updatePeriod(
                    channelData[RssKeyword.Channel.UpdatePeriod.value]?.trim()
                )

                // Itunes Data
                channelFactory.itunesChannelBuilder.type(
                    channelData[RssKeyword.Channel.Itunes.Type.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.explicit(
                    channelData[RssKeyword.Itunes.Explicit.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.subtitle(
                    channelData[RssKeyword.Itunes.Subtitle.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.summary(
                    channelData[RssKeyword.Itunes.Summary.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.author(
                    channelData[RssKeyword.Itunes.Author.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.duration(
                    channelData[RssKeyword.Itunes.Duration.value]?.trim()
                )
                channelFactory.setChannelItunesKeywords(
                    channelData[RssKeyword.Itunes.Keywords.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.newsFeedUrl(
                    channelData[RssKeyword.Channel.Itunes.NewFeedUrl.value]?.trim()
                )

                isInsideChannel = false
            }

            RssKeyword.Item.Item.value -> {
                channelFactory.articleBuilder.author(
                    itemData[RssKeyword.Item.Author.value]?.trim()
                )
                channelFactory.articleBuilder.sourceName(
                    itemData[RssKeyword.Item.Source.value]?.trim()
                )

                val time = itemData[RssKeyword.Item.Time.value]?.trim()
                if (time != null) {
                    channelFactory.articleBuilder.pubDate(time)
                } else {
                    channelFactory.articleBuilder.pubDate(
                        itemData[RssKeyword.Item.PubDate.value]?.trim()
                    )
                }

                channelFactory.articleBuilder.guid(
                    itemData[RssKeyword.Item.Guid.value]?.trim()
                )
                itemData[RssKeyword.Item.Content.value]?.trim()?.let { content ->
                    channelFactory.setImageFromContent(content)
                    channelFactory.articleBuilder.content(content)
                }
                channelFactory.articleBuilder.image(
                    itemData[RssKeyword.Item.News.Image.value]?.trim()
                )
                channelFactory.articleBuilder.commentUrl(
                    itemData[RssKeyword.Item.Comments.value]?.trim()
                )
                channelFactory.articleBuilder.image(
                    itemData[RssKeyword.Image.value]?.trim()
                )
                channelFactory.articleBuilder.title(
                    itemData[RssKeyword.Title.value]?.trim()
                )
                channelFactory.articleBuilder.link(
                    itemData[RssKeyword.Link.value]?.trim()
                )
                itemData[RssKeyword.Description.value]?.trim()?.let { description ->
                    channelFactory.setImageFromContent(description)
                    channelFactory.articleBuilder.description(description)
                }

                channelFactory.articleBuilder.image(
                    itemData[RssKeyword.Item.Enclosure.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.episode(
                    itemData[RssKeyword.Item.Itunes.Episode.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.episodeType(
                    itemData[RssKeyword.Item.Itunes.EpisodeType.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.season(
                    itemData[RssKeyword.Item.Itunes.Season.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.explicit(
                    itemData[RssKeyword.Itunes.Explicit.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.subtitle(
                    itemData[RssKeyword.Itunes.Subtitle.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.summary(
                    itemData[RssKeyword.Itunes.Summary.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.author(
                    itemData[RssKeyword.Itunes.Author.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.duration(
                    itemData[RssKeyword.Itunes.Duration.value]?.trim()
                )
                channelFactory.setArticleItunesKeywords(
                    itemData[RssKeyword.Itunes.Keywords.value]?.trim()
                )
                channelFactory.buildArticle()
                itemData.clear()
            }

            RssKeyword.Channel.Itunes.Owner.value -> {
                channelFactory.itunesOwnerBuilder.name(
                    itunesOwnerData[RssKeyword.Channel.Itunes.OwnerName.value]?.trim()
                )
                channelFactory.itunesOwnerBuilder.email(
                    itunesOwnerData[RssKeyword.Channel.Itunes.OwnerEmail.value]?.trim()
                )
                channelFactory.buildItunesOwner()
                isInsideItunesOwner = false
            }

            RssKeyword.Link.value -> {
                if (isInsideItem) {
                    isInsideItemImage = false
                }
            }

            RssKeyword.Image.value -> {
                channelFactory.channelImageBuilder.url(
                    channelImageData[RssKeyword.Url.value]?.trim()
                )
                channelFactory.channelImageBuilder.title(
                    channelImageData[RssKeyword.Title.value]?.trim()
                )
                channelFactory.channelImageBuilder.link(
                    channelImageData[RssKeyword.Link.value]?.trim()
                )
                channelFactory.channelImageBuilder.description(
                    channelImageData[RssKeyword.Description.value]?.trim()
                )
                if (isInsideItem) {
                    isInsideItemImage = false
                } else {
                    isInsideChannelImage = false
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
