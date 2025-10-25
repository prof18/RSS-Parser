package com.prof18.rssparser.internal.rdf

import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.FeedHandler
import com.prof18.rssparser.internal.RdfKeyword
import com.prof18.rssparser.model.RssChannel
import org.xml.sax.Attributes

internal class RdfFeedHandler : FeedHandler {
    private var isInsideItem = false
    private var isInsideChannel = true

    private var channelFactory = ChannelFactory()

    override fun onStartRssElement(qName: String?, attributes: Attributes?) {
        when (qName) {
            RdfKeyword.CHANNEL.value -> isInsideChannel = true
            RdfKeyword.ITEM.value -> isInsideItem = true

            RdfKeyword.CHANNEL_IMAGE.value -> {
                if (isInsideChannel) {
                    val imageUrl = attributes?.getValue(RdfKeyword.CHANNEL_IMAGE_RESOURCE.value)
                    channelFactory.channelImageBuilder.url(imageUrl)
                }
            }
        }
    }

    override fun endElement(qName: String?, text: String) {
        when {
            isInsideItem -> {
                when (qName) {
                    RdfKeyword.ITEM.value -> {
                        channelFactory.buildArticle()
                        isInsideItem = false
                    }

                    RdfKeyword.TITLE.value -> channelFactory.articleBuilder.title(text)
                    RdfKeyword.DESCRIPTION.value -> channelFactory.articleBuilder.description(text)
                    RdfKeyword.LINK.value -> channelFactory.articleBuilder.link(text)
                    RdfKeyword.DC_DATE.value -> channelFactory.articleBuilder.pubDate(text)
                    RdfKeyword.ITEM_DC_CREATOR.value -> channelFactory.articleBuilder.author(text)
                    RdfKeyword.ITEM_DC_SUBJECT.value -> channelFactory.articleBuilder.addCategory(text)
                }
            }

            isInsideChannel -> {
                when (qName) {
                    RdfKeyword.CHANNEL.value -> isInsideChannel = false
                    RdfKeyword.TITLE.value -> channelFactory.channelBuilder.title(text)
                    RdfKeyword.DESCRIPTION.value -> channelFactory.channelBuilder.description(text)
                    RdfKeyword.LINK.value -> channelFactory.channelBuilder.link(text)
                    RdfKeyword.DC_DATE.value -> channelFactory.channelBuilder.lastBuildDate(text)
                    RdfKeyword.CHANNEL_UPDATE_PERIOD.value -> channelFactory.channelBuilder.updatePeriod(text)
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
