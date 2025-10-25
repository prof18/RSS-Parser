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
            RssKeyword.CHANNEL.value -> isInsideChannel = true
            RssKeyword.ITEM.value -> isInsideItem = true
            RssKeyword.CHANNEL_ITUNES_OWNER.value -> isInsideItunesOwner = true
            RssKeyword.IMAGE.value -> {
                when {
                    isInsideItem -> {
                        isInsideItemImage = true
                    }

                    isInsideChannel -> isInsideChannelImage = true
                }
            }

            RssKeyword.ITEM_MEDIA_CONTENT.value -> {
                if (isInsideItem) {
                    val url = attributes.getValueOrNull(RssKeyword.URL.value) as? String
                    channelFactory.articleBuilder.image(url)
                }
            }

            RssKeyword.ITEM_THUMBNAIL.value -> {
                if (isInsideItem) {
                    val url = attributes.getValueOrNull(RssKeyword.URL.value) as? String
                    channelFactory.articleBuilder.image(url)
                }
            }

            RssKeyword.ITEM_ENCLOSURE.value -> {
                if (isInsideItem) {
                    val type = attributes[RssKeyword.ITEM_TYPE.value] as? String
                    val length = attributes[RssKeyword.ITEM_LENGTH.value] as? String
                    val url = attributes[RssKeyword.URL.value] as? String

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
                    }
                }
            }

            RssKeyword.ITUNES_IMAGE.value -> {
                val url = attributes.getValueOrNull(RssKeyword.HREF.value) as? String
                when {
                    isInsideItem -> channelFactory.itunesArticleBuilder.image(url)
                    isInsideChannel -> channelFactory.itunesChannelBuilder.image(url)
                }
            }

            RssKeyword.ITEM_SOURCE.value -> {
                if (isInsideItem) {
                    val sourceUrl = attributes.getValueOrNull(RssKeyword.URL.value) as? String
                    channelFactory.articleBuilder.sourceUrl(sourceUrl)
                }
            }

            RssKeyword.CHANNEL_ITUNES_CATEGORY.value -> {
                if (isInsideChannel) {
                    val category =
                        attributes.getValueOrNull(RssKeyword.CHANNEL_ITUNES_TEXT.value) as? String
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

            isInsideItem && element == RssKeyword.ITEM_CATEGORY.value -> {
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
            RssKeyword.CHANNEL.value -> {
                channelFactory.channelBuilder.title(
                    channelData[RssKeyword.TITLE.value]?.trim()
                )
                channelFactory.channelBuilder.link(
                    channelData[RssKeyword.LINK.value]?.trim()
                )
                channelFactory.channelBuilder.description(
                    channelData[RssKeyword.DESCRIPTION.value]?.trim()
                )
                channelFactory.channelBuilder.lastBuildDate(
                    channelData[RssKeyword.CHANNEL_LAST_BUILD_DATE.value]?.trim()
                )
                channelFactory.channelBuilder.updatePeriod(
                    channelData[RssKeyword.CHANNEL_UPDATE_PERIOD.value]?.trim()
                )

                // Itunes Data
                channelFactory.itunesChannelBuilder.type(
                    channelData[RssKeyword.CHANNEL_ITUNES_TYPE.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.explicit(
                    channelData[RssKeyword.ITUNES_EXPLICIT.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.subtitle(
                    channelData[RssKeyword.ITUNES_SUBTITLE.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.summary(
                    channelData[RssKeyword.ITUNES_SUMMARY.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.author(
                    channelData[RssKeyword.ITUNES_AUTHOR.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.duration(
                    channelData[RssKeyword.ITUNES_DURATION.value]?.trim()
                )
                channelFactory.setChannelItunesKeywords(
                    channelData[RssKeyword.ITUNES_KEYWORDS.value]?.trim()
                )
                channelFactory.itunesChannelBuilder.newsFeedUrl(
                    channelData[RssKeyword.CHANNEL_ITUNES_NEW_FEED_URL.value]?.trim()
                )

                isInsideChannel = false
            }

            RssKeyword.ITEM.value -> {
                channelFactory.articleBuilder.author(
                    itemData[RssKeyword.ITEM_AUTHOR.value]?.trim()
                )
                channelFactory.articleBuilder.sourceName(
                    itemData[RssKeyword.ITEM_SOURCE.value]?.trim()
                )

                val time = itemData[RssKeyword.ITEM_TIME.value]?.trim()
                if (time != null) {
                    channelFactory.articleBuilder.pubDate(time)
                } else {
                    channelFactory.articleBuilder.pubDate(
                        itemData[RssKeyword.ITEM_PUB_DATE.value]?.trim()
                    )
                }

                channelFactory.articleBuilder.pubDateIfNull(
                    itemData[RssKeyword.ITEM_DATE.value]?.trim()
                )

                channelFactory.articleBuilder.guid(
                    itemData[RssKeyword.ITEM_GUID.value]?.trim()
                )
                itemData[RssKeyword.ITEM_CONTENT.value]?.trim()?.let { content ->
                    channelFactory.setImageFromContent(content)
                    channelFactory.articleBuilder.content(content)
                }
                channelFactory.articleBuilder.image(
                    itemData[RssKeyword.ITEM_NEWS_IMAGE.value]?.trim()
                )
                channelFactory.articleBuilder.commentUrl(
                    itemData[RssKeyword.ITEM_COMMENTS.value]?.trim()
                )
                channelFactory.articleBuilder.image(
                    itemData[RssKeyword.IMAGE.value]?.trim()
                )
                channelFactory.articleBuilder.title(
                    itemData[RssKeyword.TITLE.value]?.trim()
                )
                channelFactory.articleBuilder.link(
                    itemData[RssKeyword.LINK.value]?.trim()
                )
                itemData[RssKeyword.DESCRIPTION.value]?.trim()?.let { description ->
                    channelFactory.setImageFromContent(description)
                    channelFactory.articleBuilder.description(description)
                }

                channelFactory.articleBuilder.image(
                    itemData[RssKeyword.ITEM_ENCLOSURE.value]?.trim()
                )
                channelFactory.articleBuilder.image(
                    itemData[RssKeyword.ITEM_THUMB.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.episode(
                    itemData[RssKeyword.ITEM_ITUNES_EPISODE.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.episodeType(
                    itemData[RssKeyword.ITEM_ITUNES_EPISODE_TYPE.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.season(
                    itemData[RssKeyword.ITEM_ITUNES_SEASON.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.explicit(
                    itemData[RssKeyword.ITUNES_EXPLICIT.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.subtitle(
                    itemData[RssKeyword.ITUNES_SUBTITLE.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.summary(
                    itemData[RssKeyword.ITUNES_SUMMARY.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.author(
                    itemData[RssKeyword.ITUNES_AUTHOR.value]?.trim()
                )
                channelFactory.itunesArticleBuilder.duration(
                    itemData[RssKeyword.ITUNES_DURATION.value]?.trim()
                )
                channelFactory.setArticleItunesKeywords(
                    itemData[RssKeyword.ITUNES_KEYWORDS.value]?.trim()
                )
                channelFactory.buildArticle()
                itemData.clear()
            }

            RssKeyword.CHANNEL_ITUNES_OWNER.value -> {
                channelFactory.itunesOwnerBuilder.name(
                    itunesOwnerData[RssKeyword.CHANNEL_ITUNES_OWNER_NAME.value]?.trim()
                )
                channelFactory.itunesOwnerBuilder.email(
                    itunesOwnerData[RssKeyword.CHANNEL_ITUNES_OWNER_EMAIL.value]?.trim()
                )
                channelFactory.buildItunesOwner()
                isInsideItunesOwner = false
            }

            RssKeyword.LINK.value -> {
                if (isInsideItem) {
                    isInsideItemImage = false
                }
            }

            RssKeyword.IMAGE.value -> {
                channelFactory.channelImageBuilder.url(
                    channelImageData[RssKeyword.URL.value]?.trim()
                )
                channelFactory.channelImageBuilder.title(
                    channelImageData[RssKeyword.TITLE.value]?.trim()
                )
                channelFactory.channelImageBuilder.link(
                    channelImageData[RssKeyword.LINK.value]?.trim()
                )
                channelFactory.channelImageBuilder.description(
                    channelImageData[RssKeyword.DESCRIPTION.value]?.trim()
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
