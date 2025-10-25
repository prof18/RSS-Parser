package com.prof18.rssparser.internal.atom

import com.prof18.rssparser.internal.AtomKeyword
import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.getValueOrNull
import com.prof18.rssparser.model.RssChannel

internal class AtomFeedHandler(
    private val baseFeedUrl: String?,
) : FeedHandler {

    private var currentElement: String? = null

    private var channelFactory = ChannelFactory()
    private var itemData: MutableMap<String, String> = mutableMapOf()
    private var channelData: MutableMap<String, String> = mutableMapOf()

    private var isInsideItem = false
    private var isInsideChannel = true

    override fun didStartElement(startElement: String, attributes: Map<Any?, *>) {
        currentElement = startElement

        when (currentElement) {
            AtomKeyword.ATOM.value -> isInsideChannel = true
            AtomKeyword.ENTRY_ITEM.value -> isInsideItem = true

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
                val url = attributes.getValueOrNull(AtomKeyword.YOUTUBE_MEDIA_GROUP_CONTENT_URL.value) as? String
                channelFactory.youtubeItemDataBuilder.videoUrl(url)
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL.value -> {
                val url = attributes.getValueOrNull(AtomKeyword.YOUTUBE_MEDIA_GROUP_THUMBNAIL_URL.value) as? String
                channelFactory.youtubeItemDataBuilder.thumbnailUrl(url)
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS.value -> {
                val views = attributes.getValueOrNull(
                    AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STATISTICS_VIEWS.value,
                ) as? String
                channelFactory.youtubeItemDataBuilder.viewsCount(views?.toIntOrNull())
            }

            AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING.value -> {
                val count = attributes.getValueOrNull(
                    AtomKeyword.YOUTUBE_MEDIA_GROUP_COMMUNITY_STAR_RATING_COUNT.value,
                ) as? String
                channelFactory.youtubeItemDataBuilder.likesCount(count?.toIntOrNull())
            }
        }
    }

    override fun foundCharacters(characters: String) {
        val element = currentElement ?: return

        when {
            isInsideItem -> itemData[element] = (itemData[element].orEmpty()) + characters
            isInsideChannel -> channelData[element] = (channelData[element].orEmpty()) + characters
        }
    }

    override fun didEndElement(endElement: String) {
        when (endElement) {
            AtomKeyword.ATOM.value -> {
                channelFactory.channelImageBuilder.url(
                    channelData[AtomKeyword.ICON.value]?.trim()
                )
                channelFactory.channelBuilder.lastBuildDate(
                    channelData[AtomKeyword.UPDATED.value]?.trim()
                )
                channelFactory.channelBuilder.description(
                    channelData[AtomKeyword.SUBTITLE.value]?.trim()
                )
                channelFactory.channelBuilder.title(
                    channelData[AtomKeyword.TITLE.value]?.trim()
                )

                isInsideChannel = false
            }

            AtomKeyword.ENTRY_ITEM.value -> {
                val pubDate = if (itemData[AtomKeyword.ENTRY_PUBLISHED.value] != null) {
                    itemData[AtomKeyword.ENTRY_PUBLISHED.value]?.trim()
                } else {
                    itemData[AtomKeyword.UPDATED.value]?.trim()
                }
                channelFactory.articleBuilder.pubDate(
                    pubDate
                )
                channelFactory.articleBuilder.title(
                    itemData[AtomKeyword.TITLE.value]?.trim()
                )
                channelFactory.articleBuilder.author(
                    itemData[AtomKeyword.ENTRY_AUTHOR.value]?.trim()
                )
                channelFactory.articleBuilder.guid(
                    itemData[AtomKeyword.ENTRY_GUID.value]?.trim()
                )

                val content = itemData[AtomKeyword.ENTRY_CONTENT.value]?.trim()
                channelFactory.articleBuilder.content(content)
                channelFactory.setImageFromContent(content)

                val description = itemData[AtomKeyword.ENTRY_DESCRIPTION.value]?.trim()
                channelFactory.articleBuilder.description(description)
                channelFactory.setImageFromContent(description)

                val category = itemData[AtomKeyword.ENTRY_CATEGORY.value]?.trim()
                if (!category.isNullOrEmpty()) {
                    channelFactory.articleBuilder.addCategory(category)
                }

                // Youtube

                val channelId = itemData[AtomKeyword.YOUTUBE_CHANNEL_ID.value]?.trim()
                channelFactory.youtubeChannelDataBuilder.channelId(channelId)

                val videoId = itemData[AtomKeyword.YOUTUBE_VIDEO_ID.value]?.trim()
                channelFactory.youtubeItemDataBuilder.videoId(videoId)

                val title = itemData[AtomKeyword.YOUTUBE_MEDIA_GROUP_TITLE.value]?.trim()
                channelFactory.youtubeItemDataBuilder.title(title)

                val videoDescription = itemData[AtomKeyword.YOUTUBE_MEDIA_GROUP_DESCRIPTION.value]?.trim()
                channelFactory.youtubeItemDataBuilder.description(videoDescription)

                channelFactory.buildArticle()
                itemData.clear()
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
