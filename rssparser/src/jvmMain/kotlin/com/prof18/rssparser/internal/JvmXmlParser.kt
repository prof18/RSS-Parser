package com.prof18.rssparser.internal

import com.prof18.rssparser.internal.atom.AtomFeedHandler
import com.prof18.rssparser.internal.rss.RssFeedHandler
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.internal.closeQuietly
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.nio.charset.Charset
import javax.xml.parsers.SAXParserFactory

internal class JvmXmlParser(
    private val charset: Charset? = null,
    private val dispatcher: CoroutineDispatcher,
) : XmlParser {
    override suspend fun parseXML(input: ParserInput): RssChannel = withContext(dispatcher) {
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
    private var feedHandler: FeedHandler? = null
    private val textBuilder: StringBuilder = StringBuilder()

    fun getChannel(): RssChannel =
        requireNotNull(feedHandler).buildRssChannel()

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?,
    ) {
        textBuilder.setLength(0)

        when (qName) {
            RssKeyword.Rss.value -> {
                feedHandler = RssFeedHandler()
            }
            AtomKeyword.Atom.value -> {
                feedHandler = AtomFeedHandler()
            }
            else -> feedHandler?.onStartRssElement(qName, attributes)
        }
    }

    override fun endElement(
        uri: String?,
        localName: String?,
        qName: String?,
    ) {
        val text = textBuilder.toString().trim()
        feedHandler?.endElement(qName, text)
    }

    override fun characters(ch: CharArray, start: Int, length: Int) {
        textBuilder.append(String(ch, start, length))
    }
}
