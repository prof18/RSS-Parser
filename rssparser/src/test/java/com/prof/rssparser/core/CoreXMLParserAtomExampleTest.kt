package com.prof.rssparser.core

import com.prof.rssparser.testutils.BaseCoreXMLParserTest

class CoreXMLParserAtomExampleTest: BaseCoreXMLParserTest(
    feedPath = "/atom-test-example.xml",
    channelTitle = "Example Feed",
    channelLink = "http://example.org/",
    channelDescription = "A subtitle.",
    channelImage = null,
    channelLastBuildDate = null,
    channelUpdatePeriod = null,
    channelItunesData = null,
    articleGuid = "urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a",
    articleTitle = "Atom-Powered Robots Run Amok",
    articleAuthor = "John Doe",
    articleLink = "http://example.org/2003/12/13/atom03.html",
    articlePubDate = "2003-12-13T18:30:02Z",
    articleDescription = "Some text.",
    articleContent = null,
    articleImage = null,
    articleAudio = null,
    articleVideo = null,
    articleSourceName = null,
    articleSourceUrl = null,
    articleCategories = listOf(),
    articleCommentsUrl = null,
    articleItunesData = null
)
