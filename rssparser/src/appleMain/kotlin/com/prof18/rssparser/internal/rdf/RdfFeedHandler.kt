package com.prof18.rssparser.internal.rdf

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.RdfKeyword
import com.prof18.rssparser.model.RssChannel

internal class RdfFeedHandler : FeedHandler {

    private var channelFactory = ChannelFactory()

    private var isInsideItem = false
    private var isInsideChannel = true

    override fun didStartElement(startElement: String, attributes: Map<Any?, *>) {
        when (startElement) {
            RdfKeyword.RDF.value -> isInsideChannel = true
            RdfKeyword.ITEM.value -> isInsideItem = true

            RdfKeyword.CHANNEL_IMAGE.value -> {
                val url = attributes[RdfKeyword.CHANNEL_IMAGE_RESOURCE.value] as? String
                channelFactory.channelImageBuilder.url(url?.trim())
            }
        }
    }

    override fun didEndElement(endElement: String, text: String) {
        when (endElement) {
            RdfKeyword.RDF.value -> {
                isInsideChannel = false
            }

            RdfKeyword.ITEM.value -> {
                isInsideItem = false
                channelFactory.buildArticle()
            }

            RdfKeyword.TITLE.value -> {
                when {
                    isInsideItem -> channelFactory.articleBuilder.title(text)
                    isInsideChannel -> channelFactory.channelBuilder.title(text)
                }
            }

            RdfKeyword.LINK.value -> {
                when {
                    isInsideItem -> channelFactory.articleBuilder.link(text)
                    isInsideChannel -> channelFactory.channelBuilder.link(text)
                }
            }

            RdfKeyword.DESCRIPTION.value -> {
                when {
                    isInsideItem -> channelFactory.articleBuilder.description(text)
                    isInsideChannel -> channelFactory.channelBuilder.description(text)
                }
            }

            RdfKeyword.DC_DATE.value -> {
                when {
                    isInsideItem -> channelFactory.articleBuilder.pubDate(text)
                    isInsideChannel -> channelFactory.channelBuilder.lastBuildDate(text)
                }
            }

            RdfKeyword.ITEM_DC_CREATOR.value -> {
                if (isInsideItem) {
                    channelFactory.articleBuilder.author(text)
                }
            }

            RdfKeyword.ITEM_DC_SUBJECT.value -> {
                if (isInsideItem) {
                    channelFactory.articleBuilder.addCategory(text)
                }
            }

            RdfKeyword.CHANNEL_UPDATE_PERIOD.value -> {
                if (isInsideChannel) {
                    channelFactory.channelBuilder.updatePeriod(text)
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()

    override fun shouldClearTextBuilder(qName: String): Boolean {
        return RdfKeyword.isValid(qName)
    }
}
