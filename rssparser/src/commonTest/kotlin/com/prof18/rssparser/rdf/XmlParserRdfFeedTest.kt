package com.prof18.rssparser.rdf

import com.prof18.rssparser.BaseXmlParserTest
import com.prof18.rssparser.model.RssImage

class XmlParserRdfFeedTest : BaseXmlParserTest(
    feedPath = "rdf-feed-test.xml",
    channelTitle = "news.ORF.at",
    channelLink = "https://orf.at/",
    channelDescription = "Die aktuellsten Nachrichten auf einen Blick - aus Österreich und der ganzen Welt. In Text, Bild und Video.",
    channelLastBuildDate = "2025-01-03T10:14:24+01:00",
    channelUpdatePeriod = "hourly",
    channelImage = RssImage(
        url = "https://distrowatch.com/images/other/dw.png",
        title = null,
        link = null,
        description = null,
    ),
    articleTitle = "Kiew erneut Ziel russischer Drohnenangriffe",
    articlePubDate = "2025-01-03T10:14:24+01:00",
    articleLink = "https://orf.at/stories/3380633/",
    articleDescription = "The DistroWatch Weekly news feed is brought to you by",
    articleAuthor = "ORF Online und Teletext GmbH & Co KG",
    articleCategories = listOf("Ausland"),
)
