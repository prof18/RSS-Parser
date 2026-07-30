package com.prof18.rssparser.internal

import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.ItunesOwner
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RawMediaContent
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData

internal class ChannelFactory {
    val channelBuilder = RssChannel.Builder()
    val channelImageBuilder = RssImage.Builder()
    var articleBuilder = RssItem.Builder()
    val itunesChannelBuilder = ItunesChannelData.Builder()
    var itunesArticleBuilder = ItunesItemData.Builder()
    var itunesOwnerBuilder = ItunesOwner.Builder()
    var youtubeChannelDataBuilder = YoutubeChannelData.Builder()
    var youtubeItemDataBuilder = YoutubeItemData.Builder()
    var rawEnclosureBuilder = RawEnclosure.Builder()
    var rawMediaContentBuilder = RawMediaContent.Builder()

    // This image url is extracted from the content and the description of the rss item.
    // It's a fallback just in case there aren't any images in the enclosure tag.
    private var imageUrlFromContent: String? = null

    fun buildArticle() {
        val itunesItemData = itunesArticleBuilder.build()
        // Use iTunes image as fallback if no other image is set
        articleBuilder.image(imageUrlFromContent)
        articleBuilder.image(itunesItemData?.image)
        articleBuilder.itunesArticleData(itunesItemData)
        articleBuilder.youtubeItemData(youtubeItemDataBuilder.build())
        articleBuilder.rawEnclosure(rawEnclosureBuilder.build())
        articleBuilder.rawMediaContent(rawMediaContentBuilder.build())
        articleBuilder.build()?.let { channelBuilder.addItem(it) }
        // Reset temp data
        imageUrlFromContent = null
        articleBuilder = RssItem.Builder()
        itunesArticleBuilder = ItunesItemData.Builder()
        youtubeItemDataBuilder = YoutubeItemData.Builder()
        rawEnclosureBuilder = RawEnclosure.Builder()
        rawMediaContentBuilder = RawMediaContent.Builder()
    }

    fun buildItunesOwner() {
        itunesChannelBuilder.owner(itunesOwnerBuilder.build())
        itunesOwnerBuilder = ItunesOwner.Builder()
    }

    /**
     * Finds the first img tag and gets the src as the featured image.
     *
     * Matching bare urls by file extension is kept as a fallback for content that references an
     * image without wrapping it in a tag, but it can't be the primary source: extension-less
     * image endpoints (`.../image-service/images/urn:ard:image:abc?w=432`) would never match.
     *
     * @param content The content in which to search for the tag
     */
    fun setImageFromContent(content: String?) {
        try {
            val decoded = content
                ?.replace("&amp;amp;", "&amp;")
                ?.replace("&amp;", "&")
                ?.replace("&quot;", "\"")
                ?.replace("&lt;", "<")
                ?.replace("&gt;", ">")
                ?: return

            val imageUrl = decoded.firstImageTagSource() ?: decoded.firstBareImageUrl()
            if (imageUrl != null) {
                imageUrlFromContent = imageUrl
            }
        } catch (_: Throwable) {
            // Do nothing, on iOS it could fail for too much recursion
        }
    }

    // Deliberately no `contains("<img")` pre-check: on Kotlin/Native a case-insensitive
    // substring scan costs about as much as the regex pass it would guard, so it only slows
    // down the common case where the tag is present.
    private fun String.firstImageTagSource(): String? =
        IMG_TAG_REGEX.findAll(this)
            .flatMap { tag -> IMG_SOURCE_ATTRIBUTE_REGEX.findAll(tag.value) }
            // Only one of the quoting alternatives captures, the others come back empty.
            .mapNotNull { match -> match.groupValues.drop(1).firstOrNull { it.isNotEmpty() } }
            .map { it.trim() }
            .firstOrNull { it.isUsableImageUrl() }

    private fun String.firstBareImageUrl(): String? =
        IMAGE_URL_REGEX.findAll(this)
            .map { it.value.trim() }
            .firstOrNull { it.isUsableImageUrl() }

    private fun String.isUsableImageUrl(): Boolean =
        // Relative and data: sources can't be resolved without the item base url, and lazy
        // loading placeholders live there, so only absolute http(s) sources are accepted.
        (startsWith("http://", ignoreCase = true) || startsWith("https://", ignoreCase = true)) &&
            !contains(EMOJI_WEBSITE) &&
            !contains("/smilies/")

    fun setChannelItunesKeywords(keywords: String?) {
        val keywordList = extractItunesKeywords(keywords)
        if (keywordList.isNotEmpty()) {
            itunesChannelBuilder.keywords(keywordList)
        }
    }

    fun setArticleItunesKeywords(keywords: String?) {
        val keywordList = extractItunesKeywords(keywords)
        if (keywordList.isNotEmpty()) {
            itunesArticleBuilder.keywords(keywordList)
        }
    }

    private fun extractItunesKeywords(keywords: String?): List<String> =
        keywords?.split(",")?.mapNotNull {
            it.trim().ifEmpty {
                null
            }
        } ?: emptyList()

    fun build(): RssChannel {
        val itunesChannelData = itunesChannelBuilder.build()
        val channelImage = channelImageBuilder.build()
        if (channelImage?.isNotEmpty() == true) {
            channelBuilder.image(channelImage)
        } else if (itunesChannelData?.image != null) {
            // Use iTunes image as fallback if no standard RSS image is set
            channelBuilder.image(
                RssImage(
                    title = null,
                    url = itunesChannelData.image,
                    link = null,
                    description = null
                )
            )
        }
        channelBuilder.itunesChannelData(itunesChannelData)
        channelBuilder.youtubeChannelData(youtubeChannelDataBuilder.build())
        return channelBuilder.build()
    }

    private companion object {
        const val EMOJI_WEBSITE = "https://s.w.org/images/core/emoji"

        val IMG_TAG_REGEX = Regex(
            pattern = """<img\b[^>]*>""",
            options = setOf(RegexOption.IGNORE_CASE)
        )

        // The leading delimiter keeps "src" from matching the tail of "data-src", so both
        // attributes stay separate candidates instead of the first one swallowing the second.
        val IMG_SOURCE_ATTRIBUTE_REGEX = Regex(
            pattern = """[\s"'](?:src|data-src|data-original|data-lazy-src)\s*=\s*""" +
                """(?:"([^"]*)"|'([^']*)'|([^\s"'>]+))""",
            options = setOf(RegexOption.IGNORE_CASE)
        )

        val IMAGE_URL_REGEX = Regex(
            pattern = """https?://[^\s<>"']+\.(?:jpg|jpeg|png|gif|bmp|webp)(?:\?[^\s<>"']*)?""",
            options = setOf(RegexOption.IGNORE_CASE)
        )
    }
}
