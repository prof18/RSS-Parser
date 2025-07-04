package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomLinkAlternateTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "DDay.it, news, articoli, guide, gallery e video",
        link = "https://www.dday.it",
        description = null,
        image = RssImage(
            title = null,
            url = "https://www.dday.it/images/mtile_square.png",
            link = null,
            description = null,
        ),
        lastBuildDate = "2025-02-26T20:17:26+01:00",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://www.dday.it/redazione/52184/alexa-ufficiale-parla-e-pensa-come-una-persona-e-sara-gratis-con-prime",
                title = "Alexa+ ufficiale. Parla e pensa come una persona e sarà gratis con Prime",
                author = "Roberto Pezzali",
                link = "https://www.dday.it/redazione/52184/alexa-ufficiale-parla-e-pensa-come-una-persona-e-sara-gratis-con-prime",
                pubDate = "2025-02-26T20:00:00+01:00",
                description = null,
                content = "<img src='https://images.dday.it/OWGxpArXzMvHqiZcUy4QEB7ecpjePTH7dB1nuIyMgEM/w:329/h:256/rt:fill/plain/s3://dday-production/system/uploads/news/main_image/52184/3e8e03_IMG_20250226_102016.jpg'></br>Amazon ha annunciato oggi il nuovo Alexa+. Grazie all’IA generativa e alla partnership con Anthropic Amazon ha portato Alexa ad essere quello che tutti hanno sempre desiderato, un assistente che capisce, ragiona, aiuta in casa e fa risparmiare tempo per tante piccole cose. Arriva subito in Usa, più avanti in Italia, e sarà incluso in Prime.\n" +
                    "            <a href='https://www.dday.it/redazione/52184/alexa-ufficiale-parla-e-pensa-come-una-persona-e-sara-gratis-con-prime'>... Leggi tutto</a>",
                image = "https://images.dday.it/OWGxpArXzMvHqiZcUy4QEB7ecpjePTH7dB1nuIyMgEM/w:329/h:256/rt:fill/plain/s3://dday-production/system/uploads/news/main_image/52184/3e8e03_IMG_20250226_102016.jpg",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("atom-link-alternate-href.xml")
        assertEquals(expectedChannel, channel)
    }
}
