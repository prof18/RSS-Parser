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
            AtomKeyword.Atom.value -> isInsideChannel = true
            AtomKeyword.Entry.Item.value -> isInsideItem = true

            AtomKeyword.Entry.Category.value -> {
                if (isInsideItem) {
                    val category = attributes.getValueOrNull(
                        AtomKeyword.Entry.Term.value,
                    ) as? String
                    channelFactory.articleBuilder.addCategory(category)
                }
            }

            AtomKeyword.Link.value -> {
                val href = attributes.getValueOrNull(AtomKeyword.Link.Href.value) as? String
                val rel = attributes.getValueOrNull(AtomKeyword.Link.Rel.value) as? String
                val link = if (
                    baseFeedUrl != null &&
                    rel == AtomKeyword.Link.Rel.Alternate.value &&
                    // Some feeds have full links
                    href?.startsWith("/") == true
                ) {
                    baseFeedUrl + href
                } else {
                    href
                }
                if (
                    rel != AtomKeyword.Link.Edit.value &&
                    rel != AtomKeyword.Link.Self.value &&
                    rel != AtomKeyword.Link.Rel.Enclosure.value
                ) {
                    when {
                        isInsideItem -> channelFactory.articleBuilder.link(link)
                        else -> channelFactory.channelBuilder.link(link)
                    }
                }
            }

            AtomKeyword.Youtube.MediaGroup.MediaContent.value -> {
                val url = attributes.getValueOrNull(AtomKeyword.Youtube.MediaGroup.MediaContent.Url.value) as? String
                channelFactory.youtubeItemDataBuilder.videoUrl(url)
            }

            AtomKeyword.Youtube.MediaGroup.MediaThumbnail.value -> {
                val url = attributes.getValueOrNull(AtomKeyword.Youtube.MediaGroup.MediaThumbnail.Url.value) as? String
                channelFactory.youtubeItemDataBuilder.thumbnailUrl(url)
            }

            AtomKeyword.Youtube.MediaGroup.MediaCommunity.MediaStatistics.value -> {
                val views = attributes.getValueOrNull(
                    AtomKeyword.Youtube.MediaGroup.MediaCommunity.MediaStatistics.Views.value,
                ) as? String
                channelFactory.youtubeItemDataBuilder.viewsCount(views?.toIntOrNull())
            }

            AtomKeyword.Youtube.MediaGroup.MediaCommunity.MediaStarRating.value -> {
                val count = attributes.getValueOrNull(
                    AtomKeyword.Youtube.MediaGroup.MediaCommunity.MediaStarRating.Count.value,
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
            AtomKeyword.Atom.value -> {
                channelFactory.channelImageBuilder.url(
                    channelData[AtomKeyword.Icon.value]?.trim()
                )
                channelFactory.channelBuilder.lastBuildDate(
                    channelData[AtomKeyword.Updated.value]?.trim()
                )
                channelFactory.channelBuilder.description(
                    channelData[AtomKeyword.Subtitle.value]?.trim()
                )
                channelFactory.channelBuilder.title(
                    channelData[AtomKeyword.Title.value]?.trim()
                )

                isInsideChannel = false
            }

            AtomKeyword.Entry.Item.value -> {
                val pubDate = if (itemData[AtomKeyword.Entry.Published.value] != null) {
                    itemData[AtomKeyword.Entry.Published.value]?.trim()
                } else {
                    itemData[AtomKeyword.Updated.value]?.trim()
                }
                channelFactory.articleBuilder.pubDate(
                    pubDate
                )
                channelFactory.articleBuilder.title(
                    itemData[AtomKeyword.Title.value]?.trim()
                )
                channelFactory.articleBuilder.author(
                    itemData[AtomKeyword.Entry.Author.value]?.trim()
                )
                channelFactory.articleBuilder.guid(
                    itemData[AtomKeyword.Entry.Guid.value]?.trim()
                )

                val content = itemData[AtomKeyword.Entry.Content.value]?.trim()
                channelFactory.articleBuilder.content(content)
                channelFactory.setImageFromContent(content)

                val description = itemData[AtomKeyword.Entry.Description.value]?.trim()
                channelFactory.articleBuilder.description(description)
                channelFactory.setImageFromContent(description)

                val category = itemData[AtomKeyword.Entry.Category.value]?.trim()
                if (!category.isNullOrEmpty()) {
                    channelFactory.articleBuilder.addCategory(category)
                }

                // Youtube

                val channelId = itemData[AtomKeyword.Youtube.ChannelId.value]?.trim()
                channelFactory.youtubeChannelDataBuilder.channelId(channelId)

                val videoId = itemData[AtomKeyword.Youtube.VideoId.value]?.trim()
                channelFactory.youtubeItemDataBuilder.videoId(videoId)

                val title = itemData[AtomKeyword.Youtube.MediaGroup.MediaTitle.value]?.trim()
                channelFactory.youtubeItemDataBuilder.title(title)

                val videoDescription = itemData[AtomKeyword.Youtube.MediaGroup.MediaDescription.value]?.trim()
                channelFactory.youtubeItemDataBuilder.description(videoDescription)

                channelFactory.buildArticle()
                itemData.clear()
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
