package com.prof18.rssparser.internal.rss

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.RSSKeyword
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
            RSSKeyword.Channel.Channel.value -> isInsideChannel = true
            RSSKeyword.Item.Item.value -> isInsideItem = true
            RSSKeyword.Channel.Itunes.Owner.value -> isInsideItunesOwner = true
            RSSKeyword.Image.value -> {
                when {
                    isInsideItem -> {
                        isInsideItemImage = true
                    }
                    isInsideChannel -> isInsideChannelImage = true
                }
            }

            RSSKeyword.Item.MediaContent.value -> {
                if (isInsideItem) {
                    val url = attributes?.getValue(RSSKeyword.Url.value)
                    channelFactory.articleBuilder.image(url)
                }
            }

            RSSKeyword.Item.Thumbnail.value -> {
                if (isInsideItem) {
                    val url = attributes?.getValue(RSSKeyword.Url.value)
                    channelFactory.articleBuilder.image(url)
                }
            }

            RSSKeyword.Item.Enclosure.value -> {
                if (isInsideItem) {
                    val type = attributes?.getValue(RSSKeyword.Item.Type.value)

                    when {
                        type != null && type.contains("image") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.image(
                                attributes.getValue(RSSKeyword.Url.value)
                            )
                        }

                        type != null && type.contains("audio") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.audioIfNull(
                                attributes.getValue(RSSKeyword.Url.value)
                            )
                        }

                        type != null && type.contains("video") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.videoIfNull(
                                attributes.getValue(RSSKeyword.Url.value)
                            )
                        }
                    }
                }
            }

            RSSKeyword.Itunes.Image.value -> {
                val url = attributes?.getValue(RSSKeyword.Href.value)
                when {
                    isInsideItem -> channelFactory.itunesArticleBuilder.image(url)
                    isInsideChannel -> channelFactory.itunesChannelBuilder.image(url)
                }
            }

            RSSKeyword.Item.Source.value -> {
                if (isInsideItem) {
                    val sourceUrl = attributes?.getValue(RSSKeyword.Url.value)
                    channelFactory.articleBuilder.sourceUrl(sourceUrl)
                }
            }

            RSSKeyword.Channel.Itunes.Category.value -> {
                if (isInsideChannel) {
                    val category = attributes?.getValue(RSSKeyword.Channel.Itunes.Text.value)
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
                    RSSKeyword.Link.value -> {
                        channelFactory.articleBuilder.image(text)
                    }
                    RSSKeyword.Image.value -> {
                        channelFactory.articleBuilder.image(text)
                        isInsideItemImage = false
                    }
                }
            }

            isInsideItem -> {
                when (qName) {
                    RSSKeyword.Item.Author.value -> channelFactory.articleBuilder.author(text)
                    RSSKeyword.Item.Category.value -> channelFactory.articleBuilder.addCategory(text)
                    RSSKeyword.Item.Source.value -> channelFactory.articleBuilder.sourceName(text)
                    RSSKeyword.Item.Time.value -> channelFactory.articleBuilder.pubDate(text)
                    RSSKeyword.Item.Guid.value -> channelFactory.articleBuilder.guid(text)
                    RSSKeyword.Item.Content.value -> {
                        channelFactory.articleBuilder.content(text)
                        channelFactory.setImageFromContent(text)
                    }

                    RSSKeyword.Item.PubDate.value -> {
                        channelFactory.articleBuilder.pubDate(text)
                    }

                    RSSKeyword.Item.News.Image.value -> channelFactory.articleBuilder.image(text)
                    RSSKeyword.Item.Comments.value -> channelFactory.articleBuilder.commentUrl(text)
                    RSSKeyword.Image.value -> channelFactory.articleBuilder.image(text)
                    RSSKeyword.Title.value -> channelFactory.articleBuilder.title(text)
                    RSSKeyword.Link.value -> {
                        if (!isInsideItemImage) {
                            channelFactory.articleBuilder.link(text)
                        }
                    }
                    RSSKeyword.Description.value -> {
                        channelFactory.setImageFromContent(text)
                        channelFactory.articleBuilder.description(text)
                    }

                    RSSKeyword.Item.Enclosure.value -> {
                        channelFactory.articleBuilder.image(text)
                    }

                    RSSKeyword.Item.Itunes.EpisodeType.value -> {
                        channelFactory.itunesArticleBuilder.episodeType(text)
                    }

                    RSSKeyword.Item.Itunes.Episode.value -> {
                        channelFactory.itunesArticleBuilder.episode(text)
                    }

                    RSSKeyword.Item.Itunes.Season.value -> {
                        channelFactory.itunesArticleBuilder.season(text)
                    }

                    RSSKeyword.Itunes.Explicit.value -> {
                        channelFactory.itunesArticleBuilder.explicit(text)
                    }

                    RSSKeyword.Itunes.Subtitle.value -> {
                        channelFactory.itunesArticleBuilder.subtitle(text)
                    }

                    RSSKeyword.Itunes.Summary.value -> {
                        channelFactory.itunesArticleBuilder.summary(text)
                    }

                    RSSKeyword.Itunes.Author.value -> {
                        channelFactory.itunesArticleBuilder.author(text)
                    }

                    RSSKeyword.Itunes.Duration.value -> {
                        channelFactory.itunesArticleBuilder.duration(text)
                    }

                    RSSKeyword.Itunes.Keywords.value -> channelFactory.setArticleItunesKeywords(text)

                    RSSKeyword.Item.Item.value -> {
                        channelFactory.buildArticle()
                        isInsideItem = false
                    }
                }
            }

            isInsideItunesOwner -> {
                when (qName) {
                    RSSKeyword.Channel.Itunes.OwnerName.value -> {
                        channelFactory.itunesOwnerBuilder.name(text)
                    }

                    RSSKeyword.Channel.Itunes.OwnerEmail.value -> {
                        channelFactory.itunesOwnerBuilder.email(text)
                    }

                    RSSKeyword.Channel.Itunes.Owner.value -> {
                        channelFactory.buildItunesOwner()
                        isInsideItunesOwner = false
                    }
                }
            }

            isInsideChannelImage -> {
                when (qName) {
                    RSSKeyword.Url.value -> channelFactory.channelImageBuilder.url(text)
                    RSSKeyword.Title.value -> channelFactory.channelImageBuilder.title(text)
                    RSSKeyword.Link.value -> channelFactory.channelImageBuilder.link(text)
                    RSSKeyword.Description.value -> {
                        channelFactory.channelImageBuilder.description(text)
                    }

                    RSSKeyword.Image.value -> isInsideChannelImage = false
                }
            }

            isInsideChannel -> {
                when (qName) {
                    RSSKeyword.Title.value -> channelFactory.channelBuilder.title(text)
                    RSSKeyword.Link.value -> channelFactory.channelBuilder.link(text)
                    RSSKeyword.Description.value -> channelFactory.channelBuilder.description(text)
                    RSSKeyword.Channel.LastBuildDate.value -> {
                        channelFactory.channelBuilder.lastBuildDate(text)
                    }

                    RSSKeyword.Channel.UpdatePeriod.value -> {
                        channelFactory.channelBuilder.updatePeriod(text)
                    }

                    RSSKeyword.Channel.Itunes.Type.value -> {
                        channelFactory.itunesChannelBuilder.type(text)
                    }

                    RSSKeyword.Itunes.Explicit.value -> {
                        channelFactory.itunesChannelBuilder.explicit(text)
                    }

                    RSSKeyword.Itunes.Subtitle.value -> {
                        channelFactory.itunesChannelBuilder.subtitle(text)
                    }

                    RSSKeyword.Itunes.Summary.value -> {
                        channelFactory.itunesChannelBuilder.summary(text)
                    }

                    RSSKeyword.Itunes.Author.value -> {
                        channelFactory.itunesChannelBuilder.author(text)
                    }

                    RSSKeyword.Itunes.Duration.value -> {
                        channelFactory.itunesChannelBuilder.duration(text)
                    }

                    RSSKeyword.Itunes.Keywords.value -> {
                        channelFactory.setChannelItunesKeywords(text)
                    }

                    RSSKeyword.Channel.Itunes.NewFeedUrl.value -> {
                        channelFactory.itunesChannelBuilder.newsFeedUrl(text)
                    }

                    RSSKeyword.Channel.Channel.value -> isInsideChannel = false
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()

}