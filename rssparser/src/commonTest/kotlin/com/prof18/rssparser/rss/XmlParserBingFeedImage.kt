package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserBingFeedImage : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "madrid - BingNews",
        link = "https://www.bing.com:443/news/search?q=madrid&format=rss",
        description = "Search results",
        image = RssImage(
            title = "madrid",
            url = "http://www.bing.com/rsslogo.gif",
            link = "https://www.bing.com:443/news/search?q=madrid&format=rss",
            description = null
        ),
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = null,
                title = "Real Madrid, Barcelona and Juventus 'threaten clubs withdrawing from Super League\n" +
                    "                with legal action'",
                author = null,
                link = "http://www.bing.com/news/apiclick.aspx?ref=FexRss&aid=&tid=12BE3CE268B0484F92DD9828C685E325&url=https%3a%2f%2fwww.dailymail.co.uk%2fsport%2ffootball%2farticle-9554407%2fReal-Madrid-Barcelona-Juventus-threaten-clubs-withdrawing-Super-League-legal-action.html&c=4438839993362862681&mkt=it-it",
                pubDate = "Fri, 07 May 2021 12:00:00 GMT",
                description = "The three European giants are refusing to let the plans die and have\n" +
                    "                warned their former partners they will extract millions of dollars in penalties if\n" +
                    "                they walk away from the league.",
                content = null,
                image = "http://www.bing.com/th?id=OVFT.SNpH_QAbpZOYgrEHZRyCTi&pid=News",
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
        val channel = parseFeed("feed-bing-image.xml")
        assertEquals(expectedChannel, channel)
    }
}
