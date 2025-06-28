package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
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

class XmlParserAtomTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "The Verge -  All Posts",
        link = "https://www.theverge.com/",
        description = null,
        image = RssImage(
            title = null,
            link = null,
            description = null,
            url = "https://cdn.vox-cdn.com/community_logos/52801/VER_Logomark_32x32..png"
        ),
        lastBuildDate = "2023-05-26T17:30:31-04:00",
        updatePeriod = null,
        itunesChannelData = ItunesChannelData(
            author = null,
            categories = emptyList(),
            duration = null,
            explicit = null,
            image = null,
            keywords = emptyList(),
            newsFeedUrl = null,
            owner = null,
            subtitle = null,
            summary = null,
            type = null,
        ),
        youtubeChannelData = YoutubeChannelData(channelId = null),
        items = listOf(
            RssItem(
                guid = "https://www.theverge.com/2023/5/26/23739273/google-sonos-smart-speaker-patent-lawsuit-ruling",
                title = "Sonos wins \$32.5 million patent infringement victory over Google",
                author = "Chris Welch",
                link = "https://www.theverge.com/2023/5/26/23739273/google-sonos-smart-speaker-patent-lawsuit-ruling",
                pubDate = "2023-05-26T17:30:31-04:00",
                description = null,
                content = """
<figure>
            <img alt="A photo of the Sonos Era 300 on a kitchen dining table." src="https://cdn.vox-cdn.com/thumbor/oCea2Vc5FYLWqQXGUmA4O-rRrM0=/0x0:2040x1360/1310x873/cdn.vox-cdn.com/uploads/chorus_image/image/72316887/DSCF0491.0.jpg" />
            <figcaption>Photo by Chris Welch / The Verge</figcaption>
            </figure>

            <p id="b46vcm">Google has been ordered to pay Sonos ${'$'}32.5 million for infringing on the company’s smart speaker patent. A <a href="https://www.documentcloud.org/documents/23826599-google-sonos-trial-verdict?responsive=1&amp;title=1">jury verdict</a> issued in a San Francisco courtroom on Friday found that Google’s smart speakers and media players infringed on one of two Sonos patents at issue.</p>
            <p id="keHPBL"><a href="https://www.theverge.com/2020/1/7/21055048/sonos-google-lawsuit-sues-speakers-assistant-amazon">The legal battle started in 2020</a> when Sonos accused Google of copying its patented multiroom audio technology after the companies partnered in 2013. <a href="https://www.theverge.com/2022/1/6/22871121/sonos-google-patent-itc-ruling-decision-import-ban">Sonos went on to win its case at the US International Trade Commission</a>, resulting in a limited import ban on some of the Google devices in question. Google has also <a href="https://www.theverge.com/2022/1/6/22871304/google-home-speaker-group-volume-control-changes-sonos-patent-decision">had to pull some features</a> from its lineup of smart speakers and smart displays.</p>
            <figure class="e-image">

            <cite>Image: United States District Court for the Northern District of...</cite></figure>
            <p>
            <a href="https://www.theverge.com/2023/5/26/23739273/google-sonos-smart-speaker-patent-lawsuit-ruling">Continue reading&hellip;</a>
            </p>
                """.trimIndent(),
                image = "https://cdn.vox-cdn.com/thumbor/oCea2Vc5FYLWqQXGUmA4O-rRrM0=/0x0:2040x1360/1310x873/cdn.vox-cdn.com/uploads/chorus_image/image/72316887/DSCF0491.0.jpg",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = emptyList(),
                commentsUrl = null,
                itunesItemData = ItunesItemData(
                    author = null,
                    duration = null,
                    episode = null,
                    episodeType = null,
                    explicit = null,
                    image = null,
                    keywords = emptyList(),
                    subtitle = null,
                    summary = null,
                    season = null,
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
                    url = null,
                    length = null,
                    type = null,
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-atom-test.xml")
        assertEquals(expectedChannel, channel)
    }
}
