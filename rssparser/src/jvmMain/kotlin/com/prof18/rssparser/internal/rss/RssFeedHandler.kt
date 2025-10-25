package com.prof18.rssparser.internal.rss

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.RssKeyword
import com.prof18.rssparser.model.RssChannel
import org.xml.sax.Attributes

internal class RssFeedHandler : FeedHandler {

    private var isInsideItem = false
    private var isInsideChannel = true
    private var isInsideChannelImage = false
    private var isInsideItunesOwner = false
    private var isInsideItemImage = false

    private var channelFactory = ChannelFactory()

    override fun onStartRssElement(
        qName: String?,
        attributes: Attributes?,
    ) {
        when (qName) {
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
                    val url = attributes?.getValue(RssKeyword.URL.value)
                    channelFactory.articleBuilder.image(url)
                }
            }

            RssKeyword.ITEM_THUMBNAIL.value -> {
                if (isInsideItem) {
                    val url = attributes?.getValue(RssKeyword.URL.value)
                    channelFactory.articleBuilder.image(url)
                }
            }

            RssKeyword.ITEM_ENCLOSURE.value -> {
                if (isInsideItem) {
                    val type = attributes?.getValue(RssKeyword.ITEM_TYPE.value)
                    val url = attributes?.getValue(RssKeyword.URL.value)
                    val length = attributes?.getValue(RssKeyword.ITEM_LENGTH.value)

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
                val url = attributes?.getValue(RssKeyword.HREF.value)
                when {
                    isInsideItem -> channelFactory.itunesArticleBuilder.image(url)
                    isInsideChannel -> channelFactory.itunesChannelBuilder.image(url)
                }
            }

            RssKeyword.ITEM_SOURCE.value -> {
                if (isInsideItem) {
                    val sourceUrl = attributes?.getValue(RssKeyword.URL.value)
                    channelFactory.articleBuilder.sourceUrl(sourceUrl)
                }
            }

            RssKeyword.CHANNEL_ITUNES_CATEGORY.value -> {
                if (isInsideChannel) {
                    val category = attributes?.getValue(RssKeyword.CHANNEL_ITUNES_TEXT.value)
                    channelFactory.itunesChannelBuilder.addCategory(category)
                }
            }
        }
    }

    override fun endElement(
        qName: String?,
        text: String,
    ) {
        when {
            isInsideItemImage -> {
                when (qName) {
                    RssKeyword.LINK.value -> {
                        channelFactory.articleBuilder.image(text)
                    }
                    RssKeyword.IMAGE.value -> {
                        channelFactory.articleBuilder.image(text)
                        isInsideItemImage = false
                    }
                }
            }

            isInsideItem -> {
                when (qName) {
                    RssKeyword.ITEM_AUTHOR.value -> channelFactory.articleBuilder.author(text)
                    RssKeyword.ITEM_DATE.value -> channelFactory.articleBuilder.pubDateIfNull(text)
                    RssKeyword.ITEM_CATEGORY.value -> channelFactory.articleBuilder.addCategory(text)
                    RssKeyword.ITEM_SOURCE.value -> channelFactory.articleBuilder.sourceName(text)
                    RssKeyword.ITEM_TIME.value -> channelFactory.articleBuilder.pubDate(text)
                    RssKeyword.ITEM_GUID.value -> channelFactory.articleBuilder.guid(text)
                    RssKeyword.ITEM_CONTENT.value -> {
                        channelFactory.articleBuilder.content(text)
                        channelFactory.setImageFromContent(text)
                    }

                    RssKeyword.ITEM_PUB_DATE.value -> {
                        channelFactory.articleBuilder.pubDate(text)
                    }

                    RssKeyword.ITEM_NEWS_IMAGE.value -> channelFactory.articleBuilder.image(text)
                    RssKeyword.ITEM_COMMENTS.value -> channelFactory.articleBuilder.commentUrl(text)
                    RssKeyword.IMAGE.value -> channelFactory.articleBuilder.image(text)
                    RssKeyword.TITLE.value -> channelFactory.articleBuilder.title(text)
                    RssKeyword.LINK.value -> {
                        if (!isInsideItemImage) {
                            channelFactory.articleBuilder.link(text)
                        }
                    }
                    RssKeyword.DESCRIPTION.value -> {
                        channelFactory.setImageFromContent(text)
                        channelFactory.articleBuilder.description(text)
                    }

                    RssKeyword.ITEM_ENCLOSURE.value -> {
                        channelFactory.articleBuilder.image(text)
                    }

                    RssKeyword.ITEM_THUMB.value -> {
                        channelFactory.articleBuilder.image(text)
                    }

                    RssKeyword.ITEM_ITUNES_EPISODE_TYPE.value -> {
                        channelFactory.itunesArticleBuilder.episodeType(text)
                    }

                    RssKeyword.ITEM_ITUNES_EPISODE.value -> {
                        channelFactory.itunesArticleBuilder.episode(text)
                    }

                    RssKeyword.ITEM_ITUNES_SEASON.value -> {
                        channelFactory.itunesArticleBuilder.season(text)
                    }

                    RssKeyword.ITUNES_EXPLICIT.value -> {
                        channelFactory.itunesArticleBuilder.explicit(text)
                    }

                    RssKeyword.ITUNES_SUBTITLE.value -> {
                        channelFactory.itunesArticleBuilder.subtitle(text)
                    }

                    RssKeyword.ITUNES_SUMMARY.value -> {
                        channelFactory.itunesArticleBuilder.summary(text)
                    }

                    RssKeyword.ITUNES_AUTHOR.value -> {
                        channelFactory.itunesArticleBuilder.author(text)
                    }

                    RssKeyword.ITUNES_DURATION.value -> {
                        channelFactory.itunesArticleBuilder.duration(text)
                    }

                    RssKeyword.ITUNES_KEYWORDS.value -> channelFactory.setArticleItunesKeywords(text)

                    RssKeyword.ITEM.value -> {
                        channelFactory.buildArticle()
                        isInsideItem = false
                    }
                }
            }

            isInsideItunesOwner -> {
                when (qName) {
                    RssKeyword.CHANNEL_ITUNES_OWNER_NAME.value -> {
                        channelFactory.itunesOwnerBuilder.name(text)
                    }

                    RssKeyword.CHANNEL_ITUNES_OWNER_EMAIL.value -> {
                        channelFactory.itunesOwnerBuilder.email(text)
                    }

                    RssKeyword.CHANNEL_ITUNES_OWNER.value -> {
                        channelFactory.buildItunesOwner()
                        isInsideItunesOwner = false
                    }
                }
            }

            isInsideChannelImage -> {
                when (qName) {
                    RssKeyword.URL.value -> channelFactory.channelImageBuilder.url(text)
                    RssKeyword.TITLE.value -> channelFactory.channelImageBuilder.title(text)
                    RssKeyword.LINK.value -> channelFactory.channelImageBuilder.link(text)
                    RssKeyword.DESCRIPTION.value -> {
                        channelFactory.channelImageBuilder.description(text)
                    }

                    RssKeyword.IMAGE.value -> isInsideChannelImage = false
                }
            }

            isInsideChannel -> {
                when (qName) {
                    RssKeyword.TITLE.value -> channelFactory.channelBuilder.title(text)
                    RssKeyword.LINK.value -> channelFactory.channelBuilder.link(text)
                    RssKeyword.DESCRIPTION.value -> channelFactory.channelBuilder.description(text)
                    RssKeyword.CHANNEL_LAST_BUILD_DATE.value -> {
                        channelFactory.channelBuilder.lastBuildDate(text)
                    }

                    RssKeyword.CHANNEL_UPDATE_PERIOD.value -> {
                        channelFactory.channelBuilder.updatePeriod(text)
                    }

                    RssKeyword.CHANNEL_ITUNES_TYPE.value -> {
                        channelFactory.itunesChannelBuilder.type(text)
                    }

                    RssKeyword.ITUNES_EXPLICIT.value -> {
                        channelFactory.itunesChannelBuilder.explicit(text)
                    }

                    RssKeyword.ITUNES_SUBTITLE.value -> {
                        channelFactory.itunesChannelBuilder.subtitle(text)
                    }

                    RssKeyword.ITUNES_SUMMARY.value -> {
                        channelFactory.itunesChannelBuilder.summary(text)
                    }

                    RssKeyword.ITUNES_AUTHOR.value -> {
                        channelFactory.itunesChannelBuilder.author(text)
                    }

                    RssKeyword.ITUNES_DURATION.value -> {
                        channelFactory.itunesChannelBuilder.duration(text)
                    }

                    RssKeyword.ITUNES_KEYWORDS.value -> {
                        channelFactory.setChannelItunesKeywords(text)
                    }

                    RssKeyword.CHANNEL_ITUNES_NEW_FEED_URL.value -> {
                        channelFactory.itunesChannelBuilder.newsFeedUrl(text)
                    }

                    RssKeyword.CHANNEL.value -> isInsideChannel = false
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()

    override fun shouldClearTextBuilder(qName: String?): Boolean {
        return qName?.let { RssKeyword.isValid(it) } ?: false
    }
}
