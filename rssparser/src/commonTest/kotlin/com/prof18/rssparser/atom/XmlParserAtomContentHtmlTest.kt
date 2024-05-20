package com.prof18.rssparser.atom

import com.prof18.rssparser.BaseXmlParserTest
import com.prof18.rssparser.CurrentTarget
import com.prof18.rssparser.currentTarget

class XmlParserAtomContentHtmlTest : BaseXmlParserTest(
    feedPath = "feed-test-atom-content-html.xml",
    channelTitle = "Jake Wharton",
    channelLink = "https://jakewharton.com/",
    channelLastBuildDate = "2023-04-28T20:00:30+00:00",
    articleTitle = "The state of managing state (with Compose)",
    articlePubDate = "2021-11-11T00:00:00+00:00",
    articleLink = "https://code.cash.app/the-state-of-managing-state-with-compose",
    articleGuid = "https://jakewharton.com/the-state-of-managing-state-with-compose",
    articleContent = when (currentTarget) {
        CurrentTarget.JVM -> {
            "https://code.cash.app/the-state-of-managing-state-with-compose."
        }
        CurrentTarget.APPLE -> {
            "This post was published externally on Cash App Code Blog. Read it at"
        }
        else -> {
            null
        }
    }
)
