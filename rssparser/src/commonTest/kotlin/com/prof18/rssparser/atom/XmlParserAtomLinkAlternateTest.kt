package com.prof18.rssparser.atom

import com.prof18.rssparser.BaseXmlParserTest
import com.prof18.rssparser.model.RssImage

class XmlParserAtomLinkAlternateTest : BaseXmlParserTest(
    feedPath = "atom-link-alternate-href.xml",
    channelTitle = "DDay.it, news, articoli, guide, gallery e video",
    channelLink = "https://www.dday.it",
    channelLastBuildDate = "2025-02-26T20:17:26+01:00",
    channelImage = RssImage(
        title = null,
        url = "https://www.dday.it/images/mtile_square.png",
        link = null,
        description = null,
    ),
    articleGuid = "https://www.dday.it/redazione/52184/alexa-ufficiale-parla-e-pensa-come-una-persona-e-sara-gratis-con-prime",
    articleTitle = "Alexa+ ufficiale. Parla e pensa come una persona e sarà gratis con Prime",
    articleAuthor = "Roberto Pezzali",
    articleLink = "https://www.dday.it/redazione/52184/alexa-ufficiale-parla-e-pensa-come-una-persona-e-sara-gratis-con-prime",
    articlePubDate = "2025-02-26T20:00:00+01:00",
    articleContent = "<img src='https://images.dday.it/OWGxpArXzMvHqiZcUy4QEB7ecpjePTH7dB1nuIyMgEM/w:329/h:256/rt:fill/plain/s3://dday-production/system/uploads/news/main_image/52184/3e8e03_IMG_20250226_102016.jpg'></br>Amazon ha annunciato oggi il nuovo Alexa+. Grazie all’IA generativa e alla partnership con Anthropic Amazon ha portato Alexa ad essere quello che tutti hanno sempre desiderato, un assistente che capisce, ragiona, aiuta in casa e fa risparmiare tempo per tante piccole cose. Arriva subito in Usa, più avanti in Italia, e sarà incluso in Prime.\n" +
            "            <a href='https://www.dday.it/redazione/52184/alexa-ufficiale-parla-e-pensa-come-una-persona-e-sara-gratis-con-prime'>... Leggi tutto</a>",
    articleImage = "https://images.dday.it/OWGxpArXzMvHqiZcUy4QEB7ecpjePTH7dB1nuIyMgEM/w:329/h:256/rt:fill/plain/s3://dday-production/system/uploads/news/main_image/52184/3e8e03_IMG_20250226_102016.jpg",
)