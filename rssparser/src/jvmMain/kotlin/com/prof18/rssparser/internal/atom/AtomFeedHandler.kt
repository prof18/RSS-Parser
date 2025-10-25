package com.prof18.rssparser.internal.atom

import com.prof18.rssparser.internal.AtomKeyword
import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.model.RssChannel
import org.xml.sax.Attributes

internal class AtomFeedHandler(
    private val baseFeedUrl: String?,
) : FeedHandler {

    private var isInsideItem = false
    private var isInsideChannel = true

    private var channelFactory = ChannelFactory()

    override fun onStartRssElement(qName: String?, attributes: Attributes?) {
        when (qName) {
            AtomKeyword.ATOM.value -> isInsideChannel = true
            AtomKeyword.ENTRY_ITEM.value -> isInsideItem = true

            AtomKeyword.ENTRY_CATEGORY.value -> {
                if (isInsideItem) {
                    val category = attributes?.getValue(AtomKeyword.ENTRY_TERM.value)
                    channelFactory.articleBuilder.addCategory(category)
                }
            }

            AtomKeyword.LINK.value -> {
                val href = attributes?.getValue(AtomKeyword.LINK_HREF.value)
                val rel = attributes?.getValue(AtomKeyword.LINK_REL.value)
                if (
                    rel != AtomKeyword.LINK_EDIT.value &&
                    rel != AtomKeyword.LINK_SELF.value &&
                    rel != AtomKeyword.LINK_REL_ENCLOSURE.value &&
                    rel != AtomKeyword.LINK_REL_REPLIES.value
                ) {
                    val link = if (baseFeedUrl != null &&
                        rel == AtomKeyword.LINK_REL_ALTERNATE.value &&
                        // Some feeds have full links
                        href?.startsWith("/") == true
                    ) {
                        baseFeedUrl + href
                    } else {
                        href
                    }
                    when {
                        isInsideItem -> channelFactory.articleBuilder.link(link)
                        else -> channelFactory.channelBuilder.link(link)
                    }
                }
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT.value -> {
                val url = attributes?.getValue(AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT_URL.value)
                channelFactory.youtubeItemDataBuilder.videoUrl(url)
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL.value -> {
                val url = attributes?.getValue(AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL_URL.value)
                channelFactory.youtubeItemDataBuilder.thumbnailUrl(url)
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS.value -> {
                val views = attributes?.getValue(
                    AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS_VIEWS.value
                )
                channelFactory.youtubeItemDataBuilder.viewsCount(views?.toIntOrNull())
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING.value -> {
                val count = attributes?.getValue(
                    AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING_COUNT.value
                )
                channelFactory.youtubeItemDataBuilder.likesCount(count?.toIntOrNull())
            }
        }
    }

    override fun endElement(qName: String?, text: String) {
        when {
            isInsideItem -> {
                when (qName) {
                    AtomKeyword.ENTRY_PUBLISHED.value -> channelFactory.articleBuilder.pubDate(text)
                    AtomKeyword.UPDATED.value -> channelFactory.articleBuilder.pubDateIfNull(text)
                    AtomKeyword.TITLE.value -> channelFactory.articleBuilder.title(text)
                    AtomKeyword.ENTRY_AUTHOR.value -> channelFactory.articleBuilder.author(text)
                    AtomKeyword.ENTRY_GUID.value -> channelFactory.articleBuilder.guid(text)
                    AtomKeyword.ENTRY_CONTENT.value -> {
                        channelFactory.articleBuilder.content(text)
                        channelFactory.setImageFromContent(text)
                    }
                    AtomKeyword.ENTRY_DESCRIPTION.value -> {
                        channelFactory.articleBuilder.description(text)
                        channelFactory.setImageFromContent(text)
                    }
                    AtomKeyword.ENTRY_CATEGORY.value -> {
                        if (isInsideItem) {
                            if (text.isNotEmpty()) {
                                channelFactory.articleBuilder.addCategory(text)
                            }
                        }
                    }
                    AtomKeyword.ENTRY_ITEM.value -> {
                        channelFactory.buildArticle()
                        isInsideItem = false
                    }
                    AtomKeyword.YOUTUBE_CHANNEL_ID.value -> channelFactory.youtubeChannelDataBuilder.channelId(text)
                    AtomKeyword.YOUTUBE_VIDEO_ID.value -> channelFactory.youtubeItemDataBuilder.videoId(text)
                    AtomKeyword.YOUTUBE_MEDIA_GROUP_TITLE.value -> channelFactory.youtubeItemDataBuilder.title(text)
                    AtomKeyword.YOUTUBE_MEDIA_GROUP_DESCRIPTION.value -> {
                        channelFactory.youtubeItemDataBuilder.description(text)
                    }
                }
            }

            isInsideChannel -> {
                when (qName) {
                    AtomKeyword.ICON.value -> channelFactory.channelImageBuilder.url(text)
                    AtomKeyword.UPDATED.value -> channelFactory.channelBuilder.lastBuildDate(text)
                    AtomKeyword.SUBTITLE.value -> channelFactory.channelBuilder.description(text)
                    AtomKeyword.TITLE.value -> channelFactory.channelBuilder.title(text)
                    AtomKeyword.ATOM.value -> isInsideChannel = false
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
