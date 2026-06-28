package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RawMediaContent
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserGoogleBlogFeedTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "The Official Google Blog",
        link = "https://blog.google/",
        description = "Insights from Googlers into our products, technology, and the Google culture.",
        image = RssImage(
            title = "The Keyword",
            link = "https://blog.google/",
            description = null,
            url = "https://blog.google/static/blogv2/images/google.png",
        ),
        lastBuildDate = "Fri, 26 Jun 2026 16:00:00 +0000",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://blog.google/company-news/inside-google/around-the-globe/google-europe/investing-in-ukraines-ai-leadership-and-economic-future/",
                title = "Investing in Ukraine’s AI leadership and economic future",
                author = null,
                link = "https://blog.google/company-news/inside-google/around-the-globe/google-europe/investing-in-ukraines-ai-leadership-and-economic-future/",
                pubDate = "Thu, 25 Jun 2026 17:40:00 +0000",
                description = "Google.org is providing a \$5 million grant to scale Obrii, Ukraine’s national AI job platform. This contributes to Ukraine's AI adoption, following sustained investment …",
                content = null,
                image = null,
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("Google in Europe"),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            ),
            RssItem(
                guid = "https://blog.google/products-and-platforms/products/gemini/find-job-with-google-ai-tools/",
                title = "Try these 3 Google AI tools to help find your next job.",
                author = null,
                link = "https://blog.google/products-and-platforms/products/gemini/find-job-with-google-ai-tools/",
                pubDate = "Thu, 25 Jun 2026 17:00:00 +0000",
                description = """<img src="https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Google_AI_Tools_CC_social.max-600x600.format-webp.webp">Use Google AI tools — like Career Dreamer, NotebookLM and Gemini Live — for resumes, cover letters, interview prep and more.""",
                content = null,
                image = "https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Google_AI_Tools_CC_social.max-600x600.format-webp.webp",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("NotebookLM", "Gemini", "Grow with Google"),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
                rawMediaContent = RawMediaContent(
                    url = "https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Google_AI_Tools_CC_social.max-600x600.format-webp.webp",
                    type = null,
                    medium = "image",
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-google-blog.xml")
        assertEquals(expectedChannel, channel)
    }
}
