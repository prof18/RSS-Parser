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
            RdfKeyword.Channel.value -> isInsideChannel = true
            RdfKeyword.Item.value -> isInsideItem = true

            RdfKeyword.Channel.Image.value -> {
                if (isInsideChannel) {
                    val imageUrl = attributes?.getValue(RdfKeyword.Channel.Image.Resource.value)
                    channelFactory.channelImageBuilder.url(imageUrl)
                }
            }
        }
    }

    override fun endElement(qName: String?, text: String) {
        when {
            isInsideItem -> {
                when (qName) {
                    RdfKeyword.Item.value -> {
                        channelFactory.buildArticle()
                        isInsideItem = false
                    }

                    RdfKeyword.Title.value -> channelFactory.articleBuilder.title(text)
                    RdfKeyword.Description.value -> channelFactory.articleBuilder.description(text)
                    RdfKeyword.Link.value -> channelFactory.articleBuilder.link(text)
                    RdfKeyword.DcDate.value -> channelFactory.articleBuilder.pubDate(text)
                    RdfKeyword.Item.DcCreator.value -> channelFactory.articleBuilder.author(text)
                    RdfKeyword.Item.DcSubject.value -> channelFactory.articleBuilder.addCategory(text)
                }
            }

            isInsideChannel -> {
                when (qName) {
                    RdfKeyword.Channel.value -> isInsideChannel = false
                    RdfKeyword.Title.value -> channelFactory.channelBuilder.title(text)
                    RdfKeyword.Description.value -> channelFactory.channelBuilder.description(text)
                    RdfKeyword.Link.value -> channelFactory.channelBuilder.link(text)
                    RdfKeyword.DcDate.value -> channelFactory.channelBuilder.lastBuildDate(text)
                    RdfKeyword.Channel.UpdatePeriod.value -> channelFactory.channelBuilder.updatePeriod(text)
                }
            }
        }
    }

    override fun buildRssChannel(): RssChannel =
        channelFactory.build()
}
