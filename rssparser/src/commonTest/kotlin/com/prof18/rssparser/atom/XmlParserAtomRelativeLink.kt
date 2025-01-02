package com.prof18.rssparser.atom

import com.prof18.rssparser.BASE_FEED_URL
import com.prof18.rssparser.BaseXmlParserTest

class XmlParserAtomRelativeLink : BaseXmlParserTest(
    feedPath = "atom-relative-url.xml",
    channelTitle = "Aurimas Liutikas",
    channelLink = "$BASE_FEED_URL/",
    channelDescription = "A personal page of Aurimas Liutikas.",
    channelLastBuildDate = "2024-12-19T06:13:47+00:00",
    articleTitle = "What The Distribution - What Is Inside Gradle Distribution Zips?",
    articlePubDate = "2024-12-18T00:00:00+00:00",
    articleLink = "$BASE_FEED_URL/2024/12/18/What-The-Distribution.html",
    articleGuid = "/2024/12/18/What-The-Distribution",
    articleDescription = "When I saw this post",
    articleAuthor = "",
)
