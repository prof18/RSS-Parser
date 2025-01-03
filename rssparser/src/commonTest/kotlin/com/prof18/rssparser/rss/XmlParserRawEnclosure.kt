package com.prof18.rssparser.rss

import com.prof18.rssparser.BaseXmlParserTest
import com.prof18.rssparser.model.RawEnclosure

class XmlParserRawEnclosure : BaseXmlParserTest(
    feedPath = "feed-raw-enclosure.xml",
    channelTitle = "W3Schools Home Page",
    channelLink = "https://www.w3schools.com",
    channelDescription = "Free web building tutorials",
    articleTitle = "RSS Tutorial",
    articleLink = "https://www.w3schools.com/xml/xml_rss.asp",
    articleDescription = "New RSS tutorial on W3Schools",
    rawEnclosure = RawEnclosure(
        url = "https://www.w3schools.com/media/3d.wmv",
        length = 78645,
        type = "video/wmv",
    ),
    articleVideo = "https://www.w3schools.com/media/3d.wmv",
)
