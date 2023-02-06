package com.prof.rssparser.core

import android.os.Build
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.S])
class CoreXMLParserImageEnclosure {
    private lateinit var articleList: List<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-image-enclosure.xml"
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
        assertEquals(channel.title, "Centrum dopravního výzkumu, v. v. i. (RSS 2.0)")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(
            channel.description,
            "Informace o novinkách na webu Centra dopravního výzkumu, pořádaných akcích v oblasti dopravy a odborných článcích z oblasti dopravy."
        )
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://www.cdv.cz/")
    }

    @Test
    fun channelImageTitle_isCorrect() {
        assertEquals(channel.image?.title, "Logo Centrum dopravního výzkumu, v. v. i.")
    }

    @Test
    fun channelImageLink_isCorrect() {
        assertEquals(channel.image?.link, "https://www.cdv.cz/image/logo-centrum-dopravniho-vyzkumu/")
    }

    @Test
    fun channelImageUrl_isCorrect() {
        assertEquals(channel.image?.url, "https://www.cdv.cz/")
    }

    @Test
    fun channelImageDescription_isCorrect() {
        assertNull(channel.image?.description)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertNull(channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 1)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "Podpora udržitelné aktivní mobility a nástroje práce s veřejností")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertNull(article.author)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(
            article.link,
            "https://www.cdv.cz/podpora-udrzitelne-aktivni-mobility-a-nastroje-prace-s-verejnosti/"
        )
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "22.11.2022 12:16:00")
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertNull(article.description)
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertNull(article.content)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "https://cdv.cz/image/rss-placeholder-image/")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf("event"))
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertNull(article.guid)
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertNull(article.audio)
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
}