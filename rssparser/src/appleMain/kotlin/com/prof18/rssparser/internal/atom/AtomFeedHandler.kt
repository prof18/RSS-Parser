package com.prof18.rssparser.internal.atom

import com.prof18.rssparser.internal.AtomKeyword
import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.getValueOrNull
import com.prof18.rssparser.model.RssChannel

internal class AtomFeedHandler(
    private val baseFeedUrl: String?,
) : FeedHandler {

    private var channelFactory = ChannelFactory()

    private var isInsideItem = false
    private var isInsideChannel = true
    private var isInsideYoutubeMediaGroup = false

    override fun didStartElement(startElement: String, attributes: Map<Any?, *>) {
        when (startElement) {
            AtomKeyword.ATOM.value -> isInsideChannel = true
            AtomKeyword.ENTRY_ITEM.value -> isInsideItem = true

            AtomKeyword.YOUTUBE_MEDIA_GROUP.value -> {
                if (isInsideItem) {
                    isInsideYoutubeMediaGroup = true
                }
            }

            AtomKeyword.ENTRY_CATEGORY.value -> {
                if (isInsideItem) {
                    val category = attributes.getValueOrNull(
                        AtomKeyword.ENTRY_TERM.value,
                    ) as? String
                    channelFactory.articleBuilder.addCategory(category)
                }
            }

            AtomKeyword.LINK.value -> {
                val href = attributes.getValueOrNull(AtomKeyword.LINK_HREF.value) as? String
                val rel = attributes.getValueOrNull(AtomKeyword.LINK_REL.value) as? String
                val link = if (
                    baseFeedUrl != null &&
                    rel == AtomKeyword.LINK_REL_ALTERNATE.value &&
                    // Some feeds have full links
                    href?.startsWith("/") == true
                ) {
                    baseFeedUrl + href
                } else {
                    href
                }
                if (
                    rel != AtomKeyword.LINK_EDIT.value &&
                    rel != AtomKeyword.LINK_SELF.value &&
                    rel != AtomKeyword.LINK_REL_ENCLOSURE.value &&
                    rel != AtomKeyword.LINK_REL_REPLIES.value
                ) {
                    when {
                        isInsideItem -> channelFactory.articleBuilder.link(link)
                        else -> channelFactory.channelBuilder.link(link)
                    }
                }
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT.value -> {
                if (isInsideItem && isInsideYoutubeMediaGroup) {
                    val url = attributes.getValueOrNull(AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT_URL.value) as? String
                    channelFactory.youtubeItemDataBuilder.videoUrl(url)
                }
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL.value -> {
                val url = attributes.getValueOrNull(AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL_URL.value) as? String
                if (isInsideItem) {
                    if (isInsideYoutubeMediaGroup) {
                        channelFactory.youtubeItemDataBuilder.thumbnailUrl(url)
                    } else {
                        channelFactory.articleBuilder.image(url)
                    }
                }
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS.value -> {
                if (isInsideItem && isInsideYoutubeMediaGroup) {
                    val views = attributes.getValueOrNull(
                        AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS_VIEWS.value,
                    ) as? String
                    channelFactory.youtubeItemDataBuilder.viewsCount(views?.toIntOrNull())
                }
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING.value -> {
                if (isInsideItem && isInsideYoutubeMediaGroup) {
                    val count = attributes.getValueOrNull(
                        AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING_COUNT.value,
                    ) as? String
                    channelFactory.youtubeItemDataBuilder.likesCount(count?.toIntOrNull())
                }
            }
        }
    }

    override fun didEndElement(endElement: String, text: String) {
        when (endElement) {
            AtomKeyword.ATOM.value -> {
                isInsideChannel = false
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP.value -> {
                isInsideYoutubeMediaGroup = false
            }

            AtomKeyword.ENTRY_ITEM.value -> {
                channelFactory.buildArticle()
                isInsideItem = false
                isInsideYoutubeMediaGroup = false
            }

            AtomKeyword.ICON.value -> {
                if (isInsideChannel) {
                    channelFactory.channelImageBuilder.url(text)
                }
            }

            AtomKeyword.ENTRY_PUBLISHED.value -> {
                if (isInsideItem) {
                    channelFactory.articleBuilder.pubDate(text)
                }
            }

            AtomKeyword.UPDATED.value -> {
                when {
                    isInsideItem -> channelFactory.articleBuilder.pubDateIfNull(text)
                    isInsideChannel -> channelFactory.channelBuilder.lastBuildDate(text)
                }
            }

            AtomKeyword.SUBTITLE.value -> {
                if (isInsideChannel) {
                    channelFactory.channelBuilder.description(text)
                }
            }

            AtomKeyword.TITLE.value -> {
                when {
                    isInsideItem -> channelFactory.articleBuilder.title(text)
                    isInsideChannel -> channelFactory.channelBuilder.title(text)
                }
            }

            AtomKeyword.ENTRY_AUTHOR.value -> {
                if (isInsideItem) {
                    channelFactory.articleBuilder.author(text)
                }
            }

            AtomKeyword.ENTRY_GUID.value -> {
                if (isInsideItem) {
                    channelFactory.articleBuilder.guid(text)
                }
            }

            AtomKeyword.ENTRY_CONTENT.value -> {
                if (isInsideItem) {
                    channelFactory.articleBuilder.content(text)
                    channelFactory.setImageFromContent(text)
                }
            }

            AtomKeyword.ENTRY_DESCRIPTION.value -> {
                if (isInsideItem) {
                    channelFactory.articleBuilder.description(text)
                    channelFactory.setImageFromContent(text)
                }
            }

            AtomKeyword.ENTRY_CATEGORY.value -> {
                if (isInsideItem && text.isNotEmpty()) {
                    channelFactory.articleBuilder.addCategory(text)
                }
            }

            AtomKeyword.YOUTUBE_CHANNEL_ID.value -> {
                channelFactory.youtubeChannelDataBuilder.channelId(text)
            }

            AtomKeyword.YOUTUBE_VIDEO_ID.value -> {
                if (isInsideItem) {
                    channelFactory.youtubeItemDataBuilder.videoId(text)
                }
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_TITLE.value -> {
                if (isInsideItem && isInsideYoutubeMediaGroup) {
                    channelFactory.youtubeItemDataBuilder.title(text)
                }
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_DESCRIPTION.value -> {
                if (isInsideItem && isInsideYoutubeMediaGroup) {
                    channelFactory.youtubeItemDataBuilder.description(text)
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()

    override fun shouldClearTextBuilder(qName: String): Boolean {
        return AtomKeyword.isValid(qName)
    }
}
