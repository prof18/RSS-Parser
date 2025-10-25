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
            RdfKeyword.RDF.value -> isInsideChannel = true
            RdfKeyword.ITEM.value -> isInsideItem = true

            RdfKeyword.IMAGE.value -> {
                val url = attributes[RdfKeyword.RESOURCE.value] as? String
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
            RdfKeyword.RDF.value -> {
                channelFactory.channelBuilder.title(channelData[RdfKeyword.TITLE.value]?.trim())
                channelFactory.channelBuilder.link(channelData[RdfKeyword.LINK.value]?.trim())
                channelFactory.channelBuilder.description(channelData[RdfKeyword.DESCRIPTION.value]?.trim())
                channelFactory.channelBuilder.lastBuildDate(channelData[RdfKeyword.DC_DATE.value]?.trim())
                channelFactory.channelBuilder.updatePeriod(channelData[RdfKeyword.UPDATE_PERIOD.value]?.trim())

                isInsideChannel = false
            }

            RdfKeyword.ITEM.value -> {
                channelFactory.articleBuilder.title(itemData[RdfKeyword.TITLE.value]?.trim())
                channelFactory.articleBuilder.link(itemData[RdfKeyword.LINK.value]?.trim())
                channelFactory.articleBuilder.description(itemData[RdfKeyword.DESCRIPTION.value]?.trim())
                channelFactory.articleBuilder.pubDate(itemData[RdfKeyword.DC_DATE.value]?.trim())
                channelFactory.articleBuilder.author(itemData[RdfKeyword.DC_CREATOR.value]?.trim())
                channelFactory.articleBuilder.addCategory(itemData[RdfKeyword.DC_SUBJECT.value]?.trim())

                isInsideItem = false
                channelFactory.buildArticle()
                itemData.clear()
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
