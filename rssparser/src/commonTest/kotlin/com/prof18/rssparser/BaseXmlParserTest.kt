package com.prof18.rssparser

import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class BaseXmlParserTest(
    val feedPath: String,

    // Channel Data
    val channelTitle: String? = null,
    val channelLink: String? = null,
    val channelDescription: String? = null,
    val channelImage: RssImage? = null,
    val channelLastBuildDate: String? = null,
    val channelUpdatePeriod: String? = null,
    val channelItunesData: ItunesChannelData? = null,
    val youtubeChannelData: YoutubeChannelData? = null,

    // Article Data
    val articleGuid: String? = null,
    val articleTitle: String? = null,
    val articleAuthor: String? = null,
    val articleLink: String? = null,
    val articlePubDate: String? = null,
    val articleDescription: String? = null,
    val articleContent: String? = null,
    val articleImage: String? = null,
    val articleAudio: String? = null,
    val articleVideo: String? = null,
    val articleSourceName: String? = null,
    val articleSourceUrl: String? = null,
    val articleCategories: List<String> = emptyList(),
    val articleCommentsUrl: String? = null,
    val articleItunesData: ItunesItemData? = null,
    val articleYoutubeData: YoutubeItemData? = null,
) : XmlParserTestExecutor() {

    private lateinit var channel: RssChannel
    private lateinit var article: RssItem

    @BeforeTest
    fun setUp() = runTest {
        val input = readFileFromResources(feedPath)
        channel = XmlParserFactory.createXmlParser().parseXML(input)
        article = channel.items[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals(channelTitle, channel.title)
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channelLink, channel.link)
    }

    @Test
    fun channelDescription_isCorrect() {
        assertEquals(channelDescription, channel.description)
    }

    @Test
    fun channelImage_isCorrect() {
        assertEquals(channelImage, channel.image)
    }

    @Test
    fun channelLastBuildDate_isCorrect() {
        assertEquals(channelLastBuildDate, channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertEquals(channelUpdatePeriod, channel.updatePeriod)
    }

    @Test
    fun channelItunesAuthor_isCorrect() {
        assertEquals(channelItunesData?.author, channel.itunesChannelData?.author)
    }

    @Test
    fun channelItunesCategories_isCorrect() {
        assertEquals(
            channelItunesData?.categories ?: emptyList<String>(),
            channel.itunesChannelData?.categories
        )
    }

    @Test
    fun channelItunesDuration_isCorrect() {
        assertEquals(channelItunesData?.duration, channel.itunesChannelData?.duration)
    }

    @Test
    fun channelItunesExplicit_isCorrect() {
        assertEquals(channelItunesData?.explicit, channel.itunesChannelData?.explicit)
    }

    @Test
    fun channelItunesImage_isCorrect() {
        assertEquals(channelItunesData?.image, channel.itunesChannelData?.image)
    }

    @Test
    fun channelItunesKeywords_isCorrect() {
        assertEquals(
            channelItunesData?.keywords ?: emptyList<String>(),
            channel.itunesChannelData?.keywords,
        )
    }

    @Test
    fun channelItunesNewsFeedUrl_isCorrect() {
        assertEquals(channelItunesData?.newsFeedUrl, channel.itunesChannelData?.newsFeedUrl)
    }

    @Test
    fun channelItunesOwnerName_isCorrect() {
        assertEquals(channelItunesData?.owner?.name, channel.itunesChannelData?.owner?.name)
    }

    @Test
    fun channelItunesOwnerEmail_isCorrect() {
        assertEquals(channelItunesData?.owner?.email, channel.itunesChannelData?.owner?.email)
    }

    @Test
    fun channelItunesSubtitle_isCorrect() {
        assertEquals(channelItunesData?.subtitle, channel.itunesChannelData?.subtitle)
    }

    @Test
    fun channelItunesSummary_isCorrect() {
        assertEquals(channelItunesData?.summary, channel.itunesChannelData?.summary)
    }

    @Test
    fun channelItunesType_isCorrect() {
        assertEquals(channelItunesData?.type, channel.itunesChannelData?.type)
    }

    @Test
    fun youtubeChannelId_isCorrect() {
        assertEquals(youtubeChannelData?.channelId, channel.youtubeChannelData?.channelId)
    }

    @Test
    fun articleGuid_isCorrect() {
        assertEquals(articleGuid, article.guid)
    }

    @Test
    fun articleTitle_isCorrect() {
        assertEquals(articleTitle, article.title)
    }

    @Test
    fun articleAuthor_isCorrect() {
        assertEquals(articleAuthor, article.author)
    }

    @Test
    fun articleLink_isCorrect() {
        assertEquals(articleLink, article.link)
    }

    @Test
    fun articlePubDate_isCorrect() {
        assertEquals(articlePubDate, article.pubDate)
    }

    @Test
    fun articleDescription_isCorrect() {
        assertEquals(articleDescription, article.description)
    }

    @Test
    fun articleContent_isCorrect() {
        assertEquals(articleContent, article.content)
    }

    @Test
    fun articleImage_isCorrect() {
        assertEquals(articleImage, article.image)
    }

    @Test
    fun articleAudio_isCorrect() {
        assertEquals(articleAudio, article.audio)
    }

    @Test
    fun articleVideo_isCorrect() {
        assertEquals(articleVideo, article.video)
    }

    @Test
    fun articleSourceName_isCorrect() {
        assertEquals(articleSourceName, article.sourceName)
    }

    @Test
    fun articleSourceUrl_isCorrect() {
        assertEquals(articleSourceUrl, article.sourceUrl)
    }

    @Test
    fun articleCategories_isCorrect() {
        assertEquals(articleCategories, article.categories)
    }

    @Test
    fun articleCommentsUrl_isCorrect() {
        assertEquals(articleCommentsUrl, article.commentsUrl)
    }

    @Test
    fun articleItunesAuthor_isCorrect() {
        assertEquals(articleItunesData?.author, article.itunesItemData?.author)
    }

    @Test
    fun articleItunesDuration_isCorrect() {
        assertEquals(articleItunesData?.duration, article.itunesItemData?.duration)
    }

    @Test
    fun articleItunesEpisode_isCorrect() {
        assertEquals(articleItunesData?.episode, article.itunesItemData?.episode)
    }

    @Test
    fun articleItunesEpisodeType_isCorrect() {
        assertEquals(articleItunesData?.episodeType, article.itunesItemData?.episodeType)
    }

    @Test
    fun articleItunesExplicit_isCorrect() {
        assertEquals(articleItunesData?.explicit, article.itunesItemData?.explicit)
    }

    @Test
    fun articleItunesImage_isCorrect() {
        assertEquals(articleItunesData?.image, article.itunesItemData?.image)
    }

    @Test
    fun articleItunesKeywords_isCorrect() {
        assertEquals(
            articleItunesData?.keywords ?: emptyList<String>(),
            article.itunesItemData?.keywords,
        )
    }

    @Test
    fun articleItunesSubtitle_isCorrect() {
        assertEquals(articleItunesData?.subtitle, article.itunesItemData?.subtitle)
    }

    @Test
    fun articleItunesSummary_isCorrect() {
        assertEquals(articleItunesData?.summary, article.itunesItemData?.summary)
    }

    @Test
    fun articleItunesSeason_isCorrect() {
        assertEquals(articleItunesData?.season, article.itunesItemData?.season)
    }

    @Test
    fun youtubeVideoId_isCorrect() {
        assertEquals(articleYoutubeData?.videoId, article.youtubeItemData?.videoId)
    }

    @Test
    fun youtubeTitle_isCorrect() {
        assertEquals(articleYoutubeData?.title, article.youtubeItemData?.title)
    }

    @Test
    fun youtubeVideoUrl_isCorrect() {
        assertEquals(articleYoutubeData?.videoUrl, article.youtubeItemData?.videoUrl)
    }

    @Test
    fun youtubeThumbnailUrl_isCorrect() {
        assertEquals(articleYoutubeData?.thumbnailUrl, article.youtubeItemData?.thumbnailUrl)
    }

    @Test
    fun youtubeDescription_isCorrect() {
        assertEquals(articleYoutubeData?.description, article.youtubeItemData?.description)
    }

    @Test
    fun youtubeViewsCount_isCorrect() {
        assertEquals(articleYoutubeData?.viewsCount, article.youtubeItemData?.viewsCount)
    }

    @Test
    fun youtubeLikesCount_isCorrect() {
        assertEquals(articleYoutubeData?.likesCount, article.youtubeItemData?.likesCount)
    }
}
