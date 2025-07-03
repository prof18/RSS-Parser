package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserHindiChannelImageTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "Latest News चीन News18 हिंदी",
        link = "https://hindi.news18.com/rss/khabar/world/china.xml",
        description = "Latest news from चीन Section",
        image = RssImage(
            title = "News18 हिंदी",
            url = "https://static.hindi.news18.com/ibnkhabar/uploads/2017/01/mainlogo_hindi_new.png",
            link = "https://hindi.news18.com/rss/khabar/world/china.xml",
            description = "Feed provided by News18 हिंदी",
        ),
        lastBuildDate = "January, 29 Jan 2021 17:56:15 +0530",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                title = "कैसे फैला कोरोना वायरस, WHO की टीम करेगी चीनी अधिकारियों के साथ बैठक",
                author = null,
                link = "https://hindi.news18.com/news/world/who-experts-in-wuhan-probing-coronavirus-origins-meet-chinese-scientists-nodtg-3437941.html",
                pubDate = "Friday, January 29, 2021 04:42 PM",
                description = "<img src='https://images.news18.com/ibnkhabar/uploads/2021/01/who-team-1.jpg' height='50' width='76' />कोरोना वायरस की उत्पत्ति को लेकर विश्व स्वास्थ्य संगठन (WHO) के विशेषज्ञ शुक्रवार को चीन के वुहान (Wuhan) में अधिकारियों के साथ बैठक करेंगे.",
                content = null,
                image = "https://images.news18.com/ibnkhabar/uploads/2021/01/who-team-1.jpg",
                categories = listOf(),
                guid = "https://hindi.news18.com/news/world/who-experts-in-wuhan-probing-coronavirus-origins-meet-chinese-scientists-nodtg-3437941.html",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun testXmlParserHindiChannelImage() = runTest {
        val feedChannel = parseFeed("feed-test-hindi-channel-image.xml")
        assertEquals(expectedChannel, feedChannel)
    }
}
