package com.prof18.rssparser.atom

import com.prof18.rssparser.BaseXmlParserTest
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData

class XmlParserAtomYoutube : BaseXmlParserTest(
    feedPath = "atom-feed-youtube.xml",
    channelTitle = "Kotlin by JetBrains",
    channelLink = "https://www.youtube.com/channel/UCP7uiEZIqci43m22KDl0sNw",
    youtubeChannelData = YoutubeChannelData(
        channelId = "UCP7uiEZIqci43m22KDl0sNw",
    ),
    articleTitle = "Multi-dollar String Interpolation in Kotlin | \$\$",
    articlePubDate = "2024-12-16T15:10:21+00:00",
    articleLink = "https://www.youtube.com/watch?v=ULBuIB9lHkc",
    articleAuthor = "Kotlin by JetBrains",
    articleGuid = "yt:video:ULBuIB9lHkc",
    articleYoutubeData = YoutubeItemData(
        videoId = "ULBuIB9lHkc",
        title = "Multi-dollar String Interpolation in Kotlin | \$\$",
        videoUrl = "https://www.youtube.com/v/ULBuIB9lHkc?version=3",
        thumbnailUrl = "https://i2.ytimg.com/vi/ULBuIB9lHkc/hqdefault.jpg",
        description = """
Kotlin’s string interpolation has been a much-liked feature from the beginning, and in this video, we'll learn about how JetBrains is adding more flexibility to it with multi-dollar string interpolation.

                To enable it:
                Ensure you are using Kotlin 2.1.0 or later in your project
                Add -Xmulti-dollar-interpolation as a compiler argument

                For IDE support:
                Use the latest 2024.3 version of IntelliJ IDEA with K2 mode enabled (requires restart)
                Get more info here: https://kotlinlang.org/docs/whatsnew21.html#multi-dollar-string-interpolation

                #kotlin #${'$'} #coffee
        """.trimIndent(),
        viewsCount = 4689,
        likesCount = 247,
    )
)
