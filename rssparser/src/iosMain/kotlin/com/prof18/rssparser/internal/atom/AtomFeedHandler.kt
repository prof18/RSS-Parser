package com.prof18.rssparser.internal.atom

import com.prof18.rssparser.internal.AtomKeyword
import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.getValueOrNull
import com.prof18.rssparser.model.RssChannel

internal class AtomFeedHandler : FeedHandler {

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
                if (rel != AtomKeyword.Link.Edit.value) {
                    when {
                        isInsideItem -> channelFactory.articleBuilder.link(href)
                        else -> channelFactory.channelBuilder.link(href)
                    }
                }
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
                channelFactory.articleBuilder.pubDateIfNull(
                    itemData[AtomKeyword.Updated.value]?.trim()
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

                channelFactory.buildArticle()
                itemData.clear()
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
