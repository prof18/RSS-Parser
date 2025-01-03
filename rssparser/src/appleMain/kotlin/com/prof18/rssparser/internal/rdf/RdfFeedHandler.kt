package com.prof18.rssparser.internal.rdf

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.RdfKeyword
import com.prof18.rssparser.model.RssChannel

internal class RdfFeedHandler : FeedHandler {

    private var currentElement: String? = null

    private var channelFactory = ChannelFactory()
    private var itemData: MutableMap<String, String> = mutableMapOf()
    private var channelData: MutableMap<String, String> = mutableMapOf()

    private var isInsideItem = false
    private var isInsideChannel = true

    override fun didStartElement(startElement: String, attributes: Map<Any?, *>) {
        currentElement = startElement

        when (currentElement) {
            RdfKeyword.Rdf.value -> isInsideChannel = true
            RdfKeyword.Item.value -> isInsideItem = true

            RdfKeyword.Channel.Image.value -> {
                val url = attributes[RdfKeyword.Channel.Image.Resource.value] as? String
                channelFactory.channelImageBuilder.url(url?.trim())
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
            RdfKeyword.Rdf.value -> {
                channelFactory.channelBuilder.title(channelData[RdfKeyword.Title.value]?.trim())
                channelFactory.channelBuilder.link(channelData[RdfKeyword.Link.value]?.trim())
                channelFactory.channelBuilder.description(channelData[RdfKeyword.Description.value]?.trim())
                channelFactory.channelBuilder.lastBuildDate(channelData[RdfKeyword.DcDate.value]?.trim())
                channelFactory.channelBuilder.updatePeriod(channelData[RdfKeyword.Channel.UpdatePeriod.value]?.trim())

                isInsideChannel = false
            }

            RdfKeyword.Item.value -> {
                channelFactory.articleBuilder.title(itemData[RdfKeyword.Title.value]?.trim())
                channelFactory.articleBuilder.link(itemData[RdfKeyword.Link.value]?.trim())
                channelFactory.articleBuilder.description(itemData[RdfKeyword.Description.value]?.trim())
                channelFactory.articleBuilder.pubDate(itemData[RdfKeyword.DcDate.value]?.trim())
                channelFactory.articleBuilder.author(itemData[RdfKeyword.Item.DcCreator.value]?.trim())
                channelFactory.articleBuilder.addCategory(itemData[RdfKeyword.Item.DcSubject.value]?.trim())

                isInsideItem = false
                channelFactory.buildArticle()
                itemData.clear()
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
