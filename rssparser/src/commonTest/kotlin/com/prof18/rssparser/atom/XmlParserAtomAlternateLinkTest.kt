package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomAlternateLinkTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "skribeworks",
        link = "https://skribeworks.com",
        description = "All about stories",
        image = RssImage(
            title = null,
            url = "https://skribeworks.com/wp-content/uploads/2025/06/cropped-skribe-logo-512-32x32.png",
            link = null,
            description = null,
        ),
        lastBuildDate = "2025-08-26T14:26:51Z",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://skribeworks.com/?p=5643",
                title = "Populating forms in Symfony",
                author = "skribe",
                link = "https://skribeworks.com/populating-forms-in-symfony/",
                pubDate = "2025-08-07T10:43:20Z",
                description = "I am new at Symfony. I still have a lot to learn. However, one of the things that bugged me for ages was that the docs didn’t describe any means for populating forms when editing an existing record. Yes, it presented a function for editing, and making changes to the database through doctrine, but no way for me to dynamically alter the data in the first place. Well , thanks [&#8230;]",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("Code"),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("atom-feed-alternate-link.xml")
        assertEquals(expectedChannel, channel)
    }
}