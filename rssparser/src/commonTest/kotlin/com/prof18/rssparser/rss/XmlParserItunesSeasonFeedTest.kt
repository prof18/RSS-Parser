package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.ItunesOwner
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserItunesSeasonFeedTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "With Gourley And Rust",
        link = "https://www.patreon.com/withgourleyandrust",
        description = "Nothing says slasher films and horror franchises more than an easy-listening, cozy-cast by two gentle movie lovers like <a href=\"mattgourley.com\" rel=\"noopener noreferrer\" target=\"_blank\">Matt Gourley</a> and <a href=\"twitter.com/paulrust\" rel=\"noopener noreferrer\" target=\"_blank\">Paul Rust</a>. Join them as they take you through the Jasons, the Michaels, the Freddies, and beyond. Get past seasons such as IN MYERS and KRUEGER WE TRUST, lots of bonus content, and regular episodes a week early at patreon.com/withgourleyandrust.<br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>",
        image = RssImage(
            title = "With Gourley And Rust",
            url = "https://assets.pippa.io/shows/6217ecbc4b795a924efd3b6f/1661890625869-8906b145080b9b1c29ec0f5a5069ea0c.jpeg",
            link = "https://www.patreon.com/withgourleyandrust",
            description = null
        ),
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = ItunesChannelData(
            author = "Matt Gourley and Paul Rust",
            categories = listOf(),
            duration = null,
            explicit = "yes",
            image = "https://assets.pippa.io/shows/6217ecbc4b795a924efd3b6f/1661890625869-8906b145080b9b1c29ec0f5a5069ea0c.jpeg",
            keywords = listOf(),
            newsFeedUrl = "https://feeds.acast.com/public/shows/1132daa7-9517-4bb0-89c0-6618f4d67d67",
            owner = ItunesOwner(
                name = "Matt Gourley and Paul Rust",
                email = "info+1132daa7-9517-4bb0-89c0-6618f4d67d67@mg.pippa.io",
            ),
            subtitle = "Nothing says slasher films and horror franchises more than an easy-listening, cozy-cast by two gentle movie lovers like Matt Gourley and Paul Rust. Join them as they take you through the Jasons, the Michaels, the Freddies, and beyond.",
            summary = "Nothing says slasher films and horror franchises more than an easy-listening, cozy-cast by two gentle movie lovers like <a href=\"mattgourley.com\" rel=\"noopener noreferrer\" target=\"_blank\">Matt Gourley</a> and <a href=\"twitter.com/paulrust\" rel=\"noopener noreferrer\" target=\"_blank\">Paul Rust</a>. Join them as they take you through the Jasons, the Michaels, the Freddies, and beyond. Get past seasons such as IN MYERS and KRUEGER WE TRUST, lots of bonus content, and regular episodes a week early at patreon.com/withgourleyandrust.<br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>",
            type = "episodic",
        ),
        youtubeChannelData = YoutubeChannelData(channelId = null),
        items = listOf(
            RssItem(
                title = "RASING CAIN",
                author = null,
                link = "https://www.patreon.com/withgourleyandrust",
                pubDate = "Fri, 04 Nov 2022 07:00:46 GMT",
                description = "<p>The De Palma Paradox.</p><br><p>With Gourley And Rust bonus content on <a href=\"https://www.patreon.com/withgourleyandrust\" rel=\"noopener noreferrer\" target=\"_blank\">PATREON</a> and merchandise on <a href=\"https://www.redbubble.com/people/gourleyandrust/shop\" rel=\"noopener noreferrer\" target=\"_blank\">REDBUBBLE</a>.</p><br><p>With Gourley and Rust theme song by Matt's band, <a href=\"https://open.spotify.com/artist/53j7Q32qFKfBIuop1BUmOq\" rel=\"noopener noreferrer\" target=\"_blank\">TOWNLAND</a>.</p><br><p>And also check out Paul's band, <a href=\"https://open.spotify.com/artist/3gc8ddHIjroKBbooNKDcP6?si=Cxojj4VxTquMoQcNTpTRwg&amp;dl_branch=1\" rel=\"noopener noreferrer\" target=\"_blank\">DON'T STOP OR WE'LL DIE</a>.</p><br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>",
                content = null,
                image = null,
                categories = listOf(),
                guid = "635861df5f0a000012334a6f",
                audio = "https://sphinx.acast.com/p/acast/s/with-gourley-and-rust/e/635861df5f0a000012334a6f/media.mp3",
                video = null,
                sourceName = null,
                sourceUrl = null,
                commentsUrl = null,
                itunesItemData = ItunesItemData(
                    author = null,
                    duration = "2:22:55",
                    episode = "5",
                    episodeType = "full",
                    explicit = "yes",
                    image = "https://assets.pippa.io/shows/6217ecbc4b795a924efd3b6f/1666735987433-0c7f8a23c84a67257190c040b06b4490.jpeg",
                    keywords = listOf(),
                    subtitle = null,
                    summary = "<p>The De Palma Paradox.</p><br><p>With Gourley And Rust bonus content on <a href=\"https://www.patreon.com/withgourleyandrust\" rel=\"noopener noreferrer\" target=\"_blank\">PATREON</a> and merchandise on <a href=\"https://www.redbubble.com/people/gourleyandrust/shop\" rel=\"noopener noreferrer\" target=\"_blank\">REDBUBBLE</a>.</p><br><p>With Gourley and Rust theme song by Matt's band, <a href=\"https://open.spotify.com/artist/53j7Q32qFKfBIuop1BUmOq\" rel=\"noopener noreferrer\" target=\"_blank\">TOWNLAND</a>.</p><br><p>And also check out Paul's band, <a href=\"https://open.spotify.com/artist/3gc8ddHIjroKBbooNKDcP6?si=Cxojj4VxTquMoQcNTpTRwg&amp;dl_branch=1\" rel=\"noopener noreferrer\" target=\"_blank\">DON'T STOP OR WE'LL DIE</a>.</p><br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>",
                    season = "11",
                ),
                youtubeItemData = YoutubeItemData(
                    videoId = null,
                    title = null,
                    videoUrl = null,
                    thumbnailUrl = null,
                    description = null,
                    viewsCount = null,
                    likesCount = null,
                ),
                rawEnclosure = RawEnclosure(
                    url = "https://sphinx.acast.com/p/acast/s/with-gourley-and-rust/e/635861df5f0a000012334a6f/media.mp3",
                    length = 342725492,
                    type = "audio/mpeg",
                )
            )
        )
    )

    @Test
    fun testXmlParserItunesSeasonFeed() = runTest {
        val feedChannel = parseFeed("feed-itunes-season.xml")
        assertEquals(expectedChannel, feedChannel)
    }
}
