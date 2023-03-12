package com.prof.rssparser

import java.nio.charset.Charset
import javax.xml.parsers.SAXParserFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.internal.closeQuietly
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler

class JvmXmlParser(
    private val charset: Charset? = null,
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {
    override suspend fun parseXML(input: ParserInput): Channel = withContext(dispatcher) {
        val parser = SAXParserFactory.newInstance().newSAXParser()
        val handler = SaxFeedHandler()

        if (charset != null) {
            val inputSource = InputSource(input.inputStream).apply {
                encoding = charset.toString()
            }
            parser.parse(inputSource, handler)
        } else {
            parser.parse(input.inputStream, handler)
        }

        val channel = handler.getChannel()
        input.inputStream.closeQuietly()
        return@withContext channel
    }
}

private class SaxFeedHandler : DefaultHandler() {

    // A flag just to be sure of the correct parsing
    var isInsideItem = false
    var isInsideChannel = false
    var isInsideChannelImage = false
    var isInsideItunesOwner = false

    private val textBuilder: StringBuilder = StringBuilder()

    private var channelFactory = ChannelFactory()

    fun getChannel(): Channel = channelFactory.build()

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?,
    ) {
        textBuilder.setLength(0)

        when (qName) {
            RSSKeyword.Channel.Channel.value -> isInsideChannel = true
            RSSKeyword.Item.Item.value -> isInsideItem = true
            RSSKeyword.Channel.Itunes.Owner.value -> isInsideItunesOwner = true
            RSSKeyword.Image.value -> isInsideChannelImage = isInsideChannel && !isInsideItem

            RSSKeyword.Item.MediaContent.value -> {
                if (isInsideItem) {
                    val url = attributes?.getValue(RSSKeyword.URL.value)
                    channelFactory.articleBuilder.image(url)
                }
            }

            RSSKeyword.Item.Thumbnail.value -> {
                if (isInsideItem) {
                    val url = attributes?.getValue(RSSKeyword.URL.value)
                    channelFactory.articleBuilder.image(url)
                }
            }

            RSSKeyword.Item.Enclosure.value -> {
                if (isInsideItem) {
                    val type = attributes?.getValue(RSSKeyword.Item.Type.value)

                    when {
                        type != null && type.contains("image") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.imageIfNull(
                                attributes.getValue(RSSKeyword.URL.value)
                            )
                        }

                        type != null && type.contains("audio") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.audioIfNull(
                                attributes.getValue(RSSKeyword.URL.value)
                            )
                        }

                        type != null && type.contains("video") -> {
                            // If there are multiple elements, we take only the first
                            channelFactory.articleBuilder.videoIfNull(
                                attributes.getValue(RSSKeyword.URL.value)
                            )
                        }
                    }
                }
            }

            RSSKeyword.Itunes.Image.value -> {
                val url = attributes?.getValue(RSSKeyword.HREF.value)
                when {
                    isInsideItem -> channelFactory.itunesArticleBuilder.image(url)
                    isInsideChannel -> channelFactory.itunesChannelBuilder.image(url)
                }
            }

            RSSKeyword.Item.Source.value -> {
                if (isInsideItem) {
                    val sourceUrl = attributes?.getValue(RSSKeyword.URL.value)
                    channelFactory.articleBuilder.sourceUrl(sourceUrl)
                }
            }

            RSSKeyword.Channel.Itunes.Category.value -> {
                if (isInsideChannel) {
                    val category = attributes?.getValue(RSSKeyword.Channel.Itunes.Text.value)
                    channelFactory.itunesChannelBuilder.addCategory(category)
                }
            }
        }
    }

    override fun endElement(
        uri: String?,
        localName: String?,
        qName: String?,
    ) {
        val text = textBuilder.toString().trim()

        when {
            isInsideItem -> {
                when (qName) {
                    RSSKeyword.Item.Author.value -> channelFactory.articleBuilder.author(text)
                    RSSKeyword.Item.Category.value -> channelFactory.articleBuilder.addCategory(text)
                    RSSKeyword.Item.Source.value -> channelFactory.articleBuilder.sourceName(text)
                    RSSKeyword.Item.Time.value -> channelFactory.articleBuilder.pubDate(text)
                    RSSKeyword.Item.GUID.value -> channelFactory.articleBuilder.guid(text)
                    RSSKeyword.Item.Content.value -> {
                        channelFactory.articleBuilder.content(text)
                        channelFactory.setImageFromContent(text)
                    }

                    RSSKeyword.Item.PubDate.value -> {
                        channelFactory.articleBuilder.pubDate(text)
                    }

                    RSSKeyword.Item.News.Image.value -> channelFactory.articleBuilder.image(text)
                    RSSKeyword.Item.Comments.value -> channelFactory.articleBuilder.commentUrl(text)
                    RSSKeyword.Image.value -> channelFactory.articleBuilder.image(text)
                    RSSKeyword.Title.value -> channelFactory.articleBuilder.title(text)
                    RSSKeyword.Link.value -> channelFactory.articleBuilder.link(text)
                    RSSKeyword.Description.value -> {
                        channelFactory.setImageFromContent(text)
                        channelFactory.articleBuilder.description(text)
                    }

                    RSSKeyword.Item.Enclosure.value -> {
                        channelFactory.articleBuilder.imageIfNull(text)
                    }

                    RSSKeyword.Item.Itunes.EpisodeType.value -> {
                        channelFactory.itunesArticleBuilder.episodeType(text)
                    }
                    RSSKeyword.Item.Itunes.Episode.value -> channelFactory.itunesArticleBuilder.episode(text)
                    RSSKeyword.Item.Itunes.Season.value -> channelFactory.itunesArticleBuilder.season(text)
                    RSSKeyword.Itunes.Explicit.value -> channelFactory.itunesArticleBuilder.explicit(text)
                    RSSKeyword.Itunes.Subtitle.value -> channelFactory.itunesArticleBuilder.subtitle(text)
                    RSSKeyword.Itunes.Summary.value -> channelFactory.itunesArticleBuilder.summary(text)
                    RSSKeyword.Itunes.Author.value -> channelFactory.itunesArticleBuilder.author(text)
                    RSSKeyword.Itunes.Duration.value -> channelFactory.itunesArticleBuilder.duration(text)
                    RSSKeyword.Itunes.Keywords.value -> channelFactory.setArticleItunesKeywords(text)

                    RSSKeyword.Item.Item.value -> {
                        channelFactory.buildArticle()
                        isInsideItem = false
                    }
                }
            }

            isInsideItunesOwner -> {
                when (qName) {
                    RSSKeyword.Channel.Itunes.OwnerName.value -> channelFactory.itunesOwnerBuilder.name(text)
                    RSSKeyword.Channel.Itunes.OwnerEmail.value -> channelFactory.itunesOwnerBuilder.email(text)
                    RSSKeyword.Channel.Itunes.Owner.value -> {
                        channelFactory.buildItunesOwner()
                        isInsideItunesOwner = false
                    }
                }
            }

            isInsideChannelImage -> {
                when (qName) {
                    RSSKeyword.URL.value -> channelFactory.channelImageBuilder.url(text)
                    RSSKeyword.Title.value -> channelFactory.channelImageBuilder.title(text)
                    RSSKeyword.Link.value -> channelFactory.channelImageBuilder.link(text)
                    RSSKeyword.Description.value -> channelFactory.channelImageBuilder.description(text)
                    RSSKeyword.Image.value -> isInsideChannelImage = false
                }
            }

            isInsideChannel -> {
                when (qName) {
                    RSSKeyword.Title.value -> channelFactory.channelBuilder.title(text)
                    RSSKeyword.Link.value -> channelFactory.channelBuilder.link(text)
                    RSSKeyword.Description.value -> channelFactory.channelBuilder.description(text)
                    RSSKeyword.Channel.LastBuildDate.value -> channelFactory.channelBuilder.lastBuildDate(text)
                    RSSKeyword.Channel.UpdatePeriod.value -> channelFactory.channelBuilder.updatePeriod(text)

                    RSSKeyword.Channel.Itunes.Type.value -> channelFactory.itunesChannelBuilder.type(text)
                    RSSKeyword.Itunes.Explicit.value -> channelFactory.itunesChannelBuilder.explicit(text)
                    RSSKeyword.Itunes.Subtitle.value -> channelFactory.itunesChannelBuilder.subtitle(text)
                    RSSKeyword.Itunes.Summary.value -> channelFactory.itunesChannelBuilder.summary(text)
                    RSSKeyword.Itunes.Author.value -> channelFactory.itunesChannelBuilder.author(text)
                    RSSKeyword.Itunes.Duration.value -> channelFactory.itunesChannelBuilder.duration(text)
                    RSSKeyword.Itunes.Keywords.value -> channelFactory.setChannelItunesKeywords(text)
                    RSSKeyword.Channel.Itunes.NewFeedUrl.value -> {
                        channelFactory.itunesChannelBuilder.newsFeedUrl(text)
                    }

                    RSSKeyword.Channel.Channel.value -> isInsideChannel = false
                }
            }
        }
    }

    override fun characters(ch: CharArray, start: Int, length: Int) {
        textBuilder.append(String(ch, start, length))
    }
}
