package com.prof.rssparser.core

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.Image
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserItunesSeasonFeedTest {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-itunes-season.xml"
    private lateinit var channel: Channel

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        channel = CoreXMLParser.parseXML(inputStream)
        articleList = channel.articles
        article = articleList[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals("With Gourley And Rust", channel.title)
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals("Nothing says slasher films and horror franchises more than an easy-listening, cozy-cast by two gentle movie lovers like <a href=\"mattgourley.com\" rel=\"noopener noreferrer\" target=\"_blank\">Matt Gourley</a> and <a href=\"twitter.com/paulrust\" rel=\"noopener noreferrer\" target=\"_blank\">Paul Rust</a>. Join them as they take you through the Jasons, the Michaels, the Freddies, and beyond. Get past seasons such as IN MYERS and KRUEGER WE TRUST, lots of bonus content, and regular episodes a week early at patreon.com/withgourleyandrust.<br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>", channel.description)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals("https://www.patreon.com/withgourleyandrust", channel.link)
    }

    @Test
    fun channelImage_isNull() {
        assertEquals(
            Image(
                title = "With Gourley And Rust",
                url = "https://assets.pippa.io/shows/6217ecbc4b795a924efd3b6f/1661890625869-8906b145080b9b1c29ec0f5a5069ea0c.jpeg",
                link = "https://www.patreon.com/withgourleyandrust",
                description = null
            ),
            channel.image,
        )
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertNull( channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    fun size_isCorrect() {
        assertEquals(1, articleList.size)
    }

    @Test
    fun title_isCorrect() {
        assertEquals("RASING CAIN", article.title)
    }

    @Test
    fun author_isCorrect() {
        assertNull(article.author)
    }

    @Test
    fun link_isCorrect() {
        assertEquals("https://www.patreon.com/withgourleyandrust", article.link)
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals("Fri, 04 Nov 2022 07:00:46 GMT", article.pubDate)
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(
            "<p>The De Palma Paradox.</p><br><p>With Gourley And Rust bonus content on <a href=\"https://www.patreon.com/withgourleyandrust\" rel=\"noopener noreferrer\" target=\"_blank\">PATREON</a> and merchandise on <a href=\"https://www.redbubble.com/people/gourleyandrust/shop\" rel=\"noopener noreferrer\" target=\"_blank\">REDBUBBLE</a>.</p><br><p>With Gourley and Rust theme song by Matt's band, <a href=\"https://open.spotify.com/artist/53j7Q32qFKfBIuop1BUmOq\" rel=\"noopener noreferrer\" target=\"_blank\">TOWNLAND</a>.</p><br><p>And also check out Paul's band, <a href=\"https://open.spotify.com/artist/3gc8ddHIjroKBbooNKDcP6?si=Cxojj4VxTquMoQcNTpTRwg&amp;dl_branch=1\" rel=\"noopener noreferrer\" target=\"_blank\">DON'T STOP OR WE'LL DIE</a>.</p><br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>",
            article.description,
        )
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertNull(article.content,)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories.size, 0)
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals("635861df5f0a000012334a6f", article.guid)
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertEquals("https://sphinx.acast.com/p/acast/s/with-gourley-and-rust/e/635861df5f0a000012334a6f/media.mp3", article.audio)
    }

    @Test
    @Throws
    fun sourceName_iCorrect() {
        assertNull(article.sourceName)
    }

    @Test
    @Throws
    fun sourceUrl_iCorrect() {
        assertNull(article.sourceUrl)
    }

    @Test
    @Throws
    fun video_isCorrect() {
        assertNull(article.video)
    }

    @Test
    fun itunesChannel_explicit_isCorrect() {
        assertEquals("yes", channel.itunesChannelData!!.explicit)
    }

    @Test
    fun itunesChannel_type_isCorrect() {
        assertEquals("episodic", channel.itunesChannelData!!.type)
    }

    @Test
    fun itunesChannel_subtitle_isCorrect() {
        assertEquals("Nothing says slasher films and horror franchises more than an easy-listening, cozy-cast by two gentle movie lovers like Matt Gourley and Paul Rust. Join them as they take you through the Jasons, the Michaels, the Freddies, and beyond.", channel.itunesChannelData!!.subtitle)
    }

    @Test
    fun itunesChannel_author_isCorrect() {
        assertEquals("Matt Gourley and Paul Rust", channel.itunesChannelData!!.author)
    }

    @Test
    fun itunesChannel_summary_isCorrect() {
        assertEquals(
            "Nothing says slasher films and horror franchises more than an easy-listening, cozy-cast by two gentle movie lovers like <a href=\"mattgourley.com\" rel=\"noopener noreferrer\" target=\"_blank\">Matt Gourley</a> and <a href=\"twitter.com/paulrust\" rel=\"noopener noreferrer\" target=\"_blank\">Paul Rust</a>. Join them as they take you through the Jasons, the Michaels, the Freddies, and beyond. Get past seasons such as IN MYERS and KRUEGER WE TRUST, lots of bonus content, and regular episodes a week early at patreon.com/withgourleyandrust.<br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>",
            channel.itunesChannelData!!.summary
        )
    }

    @Test
    fun itunesChannel_owner_isCorrect() {
        assertEquals("Matt Gourley and Paul Rust", channel.itunesChannelData!!.owner!!.name)
        assertEquals("info+1132daa7-9517-4bb0-89c0-6618f4d67d67@mg.pippa.io", channel.itunesChannelData!!.owner!!.email)
    }

    @Test
    fun itunesChannel_image_isCorrect() {
        assertEquals(
            "https://assets.pippa.io/shows/6217ecbc4b795a924efd3b6f/1661890625869-8906b145080b9b1c29ec0f5a5069ea0c.jpeg",
            channel.itunesChannelData!!.image
        )
    }

    @Test
    fun itunesChannel_category_isCorrect() {
        assertEquals(listOf<String>(), channel.itunesChannelData!!.categories)
    }

    @Test
    fun itunesChannel_newFeedUrl_isCorrect() {
        assertEquals("https://feeds.acast.com/public/shows/1132daa7-9517-4bb0-89c0-6618f4d67d67",channel.itunesChannelData!!.newsFeedUrl)
    }

    @Test
    fun itunesChannel_keywords_isCorrect() {
        assertTrue(channel.itunesChannelData!!.keywords.isEmpty())
    }

    @Test
    fun itunesItem_episodeType_isCorrect() {
        assertEquals("full", article.itunesArticleData!!.episodeType)
    }

    @Test
    fun itunesItem_author_isCorrect() {
        assertNull(article.itunesArticleData!!.author)
    }

    @Test
    fun itunesItem_image_isCorrect() {
        assertEquals(
            "https://assets.pippa.io/shows/6217ecbc4b795a924efd3b6f/1666735987433-0c7f8a23c84a67257190c040b06b4490.jpeg",
            article.itunesArticleData!!.image
        )
    }

    @Test
    fun itunesItem_subtitle_isCorrect() {
        assertNull( article.itunesArticleData!!.subtitle)
    }

    @Test
    fun itunesItem_summary_isCorrect() {
        assertEquals(
            "<p>The De Palma Paradox.</p><br><p>With Gourley And Rust bonus content on <a href=\"https://www.patreon.com/withgourleyandrust\" rel=\"noopener noreferrer\" target=\"_blank\">PATREON</a> and merchandise on <a href=\"https://www.redbubble.com/people/gourleyandrust/shop\" rel=\"noopener noreferrer\" target=\"_blank\">REDBUBBLE</a>.</p><br><p>With Gourley and Rust theme song by Matt's band, <a href=\"https://open.spotify.com/artist/53j7Q32qFKfBIuop1BUmOq\" rel=\"noopener noreferrer\" target=\"_blank\">TOWNLAND</a>.</p><br><p>And also check out Paul's band, <a href=\"https://open.spotify.com/artist/3gc8ddHIjroKBbooNKDcP6?si=Cxojj4VxTquMoQcNTpTRwg&amp;dl_branch=1\" rel=\"noopener noreferrer\" target=\"_blank\">DON'T STOP OR WE'LL DIE</a>.</p><br /><hr><p style='color:grey; font-size:0.75em;'> Hosted on Acast. See <a style='color:grey;' target='_blank' rel='noopener noreferrer' href='https://acast.com/privacy'>acast.com/privacy</a> for more information.</p>",
            article.itunesArticleData!!.summary,
        )
    }

    @Test
    fun itunesItem_duration_isCorrect() {
        assertEquals("2:22:55", article.itunesArticleData!!.duration)
    }

    @Test
    fun itunesItem_season_isCorrect() {
        assertEquals("11", article.itunesArticleData!!.season)
    }

    @Test
    fun itunesItem_keywords_isCorrect() {
        assertTrue( article.itunesArticleData!!.keywords.isEmpty())
    }
}