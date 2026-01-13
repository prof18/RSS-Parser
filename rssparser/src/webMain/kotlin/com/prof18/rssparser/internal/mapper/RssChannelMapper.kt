package com.prof18.rssparser.internal.mapper

import com.prof18.rssparser.internal.AtomKeyword
import com.prof18.rssparser.internal.ChannelFactory
import com.prof18.rssparser.internal.entity.AtomFeedEntity
import com.prof18.rssparser.internal.entity.AtomLinkEntity
import com.prof18.rssparser.internal.entity.FeedEntity
import com.prof18.rssparser.internal.entity.RdfFeedEntity
import com.prof18.rssparser.internal.entity.RssFeedEntity
import com.prof18.rssparser.model.RssChannel

internal fun FeedEntity.toRssChannel(baseFeedUrl: String?): RssChannel =
    when (this) {
        is RssFeedEntity -> toRssChannel()
        is RdfFeedEntity -> toRssChannel()
        is AtomFeedEntity -> toRssChannel(baseFeedUrl)
    }

private fun RssFeedEntity.toRssChannel(): RssChannel {
    val channelFactory = ChannelFactory()
    channelFactory.channelImageBuilder.url(channel.image?.url)
    channelFactory.channelImageBuilder.title(channel.image?.title?.trim())
    channelFactory.channelImageBuilder.link(channel.image?.link?.trim())
    channelFactory.channelImageBuilder.description(channel.image?.description?.trim())

    with(channelFactory.channelBuilder) {
        title(channel.title?.trim())
        lastBuildDate(channel.lastBuildDate?.trim())
        description(channel.description?.trim())
        link(channel.link)
        updatePeriod(channel.updatePeriod?.trim())
    }

    with(channelFactory.itunesChannelBuilder) {
        summary(channel.itunesSummary?.trim())
        author(channel.itunesAuthor?.trim())
        duration(channel.itunesDuration?.trim())
        explicit(channel.itunesExplicit?.trim())
        channelFactory.setChannelItunesKeywords(channel.itunesKeywords)
        channel.itunesCategories?.forEach { category ->
            addCategory(category.text?.trim() ?: "")
            category.subCategories?.forEach { subCategory ->
                addCategory(subCategory.text?.trim() ?: "")
            }
        }
        image(channel.itunesImage?.href?.trim())
        subtitle(channel.itunesSubtitle?.trim())
        type(channel.itunesType?.trim())
        newsFeedUrl(channel.newFeedUrl)
    }

    if (channel.itunesOwner != null) {
        channelFactory.itunesOwnerBuilder.name(channel.itunesOwner.name?.trim())
        channelFactory.itunesOwnerBuilder.email(channel.itunesOwner.email?.trim())
        channelFactory.buildItunesOwner()
    }
    channel.items?.forEach { entry ->
        with(channelFactory.articleBuilder) {
            title(entry.title?.trim())
            pubDate(entry.pubDate)
            pubDateIfNull(entry.dcDate?.trim())
            author(entry.author?.trim())
            content(entry.contentEncoded?.value?.trim())
            guid(entry.guid?.trim())
            link(entry.link?.trim())
            description(entry.description?.trim())
            commentUrl(entry.comments?.trim())
            image(entry.mediaContent?.url?.trim())
            image(entry.mediaContent2?.url?.trim())
            image(entry.newsImage?.link?.trim())
            image(entry.thumb?.trim())
            image(entry.image?.link?.trim())
            image(entry.mediaThumbnail?.url?.trim())
            sourceName(entry.source?.name?.trim())
            sourceUrl(entry.source?.url?.trim())
            entry.categories?.forEach { category ->
                addCategory(category.name)
            }
            channelFactory.setImageFromContent(entry.contentEncoded?.value?.trim())
            channelFactory.setImageFromContent(entry.description?.trim())
            entry.enclosures?.forEach { enclosure ->
                val type = enclosure.type?.trim()
                var url = enclosure.url?.trim()
                val length = enclosure.length?.trim()

                // If no url attribute, try to get URL from text content
                if (url == null) {
                    url = enclosure.value?.trim()
                }

                channelFactory.rawEnclosureBuilder.length(length?.toLongOrNull())
                channelFactory.rawEnclosureBuilder.type(type)
                channelFactory.rawEnclosureBuilder.url(url)

                when {
                    type != null && type.contains("image") -> {
                        // If there are multiple elements, we take only the first
                        channelFactory.articleBuilder.image(url)
                    }

                    type != null && type.contains("audio") -> {
                        // If there are multiple elements, we take only the first
                        channelFactory.articleBuilder.audioIfNull(url)
                    }

                    type != null && type.contains("video") -> {
                        // If there are multiple elements, we take only the first
                        channelFactory.articleBuilder.videoIfNull(url)
                    }

                    type == null && url != null -> {
                        // If there's no type attribute but we have a URL, assume it's an image
                        channelFactory.articleBuilder.image(url)
                    }
                }
            }
        }

        with(channelFactory.itunesArticleBuilder) {
            image(entry.itunesImage?.href?.trim())
            duration(entry.itunesDuration?.trim())
            explicit(entry.itunesExplicit?.trim())
            subtitle(entry.itunesSubtitle?.trim())
            episode(entry.itunesEpisode?.trim())
            episodeType(entry.itunesEpisodeType?.trim())
            author(entry.itunesAuthor?.trim())
            season(entry.itunesSeason?.trim())
            summary(entry.itunesSummary?.trim())
            channelFactory.setArticleItunesKeywords(entry.itunesKeywords)
        }
        channelFactory.buildArticle()
    }
    return channelFactory.build()
}

private fun RdfFeedEntity.toRssChannel(): RssChannel {
    val channelFactory = ChannelFactory()
    channelFactory.channelImageBuilder.url(channel.image?.firstOrNull()?.link)

    with(channelFactory.channelBuilder) {
        title(channel.title)
        lastBuildDate(channel.lastBuildDate)
        updatePeriod(channel.updatePeriod)
        description(channel.description?.trim())
        link(channel.link)
    }

    items?.forEach { entry ->
        with(channelFactory.articleBuilder) {
            title(entry.title?.trim())
            description(entry.description?.trim())
            link(entry.link)
            pubDate(entry.pubDate)
            author(entry.creator?.trim())
            entry.subject?.let { addCategory(it.trim()) }
        }

        channelFactory.buildArticle()
    }
    return channelFactory.build()
}

private fun AtomFeedEntity.toRssChannel(baseFeedUrl: String?): RssChannel {
    val channelFactory = ChannelFactory()
    channelFactory.channelImageBuilder.url(icon)

    with(channelFactory.channelBuilder) {
        title(title)
        lastBuildDate(updated)
        description(subtitle?.trim())
        links?.forEach { link ->
            if (link.rel != AtomKeyword.LINK_EDIT.value &&
                link.rel != AtomKeyword.LINK_SELF.value &&
                link.rel != AtomKeyword.LINK_REL_ENCLOSURE.value
            ) {
                link(link.generateLink(baseFeedUrl))
            }
        }
    }

    entries?.forEach { entry ->
        with(channelFactory.articleBuilder) {
            entry.links?.forEach { link ->
                if (link.rel != AtomKeyword.LINK_EDIT.value &&
                    link.rel != AtomKeyword.LINK_SELF.value &&
                    link.rel != AtomKeyword.LINK_REL_ENCLOSURE.value &&
                    link.rel != AtomKeyword.LINK_REL_REPLIES.value
                ) {
                    link(link.generateLink(baseFeedUrl))
                }
            }
            pubDate(entry.published)
            pubDateIfNull(entry.updated)
            title(entry.title?.trim())
            author(entry.author?.name)
            content(entry.content?.trim())
            guid(entry.id?.trim())
            description(entry.summary?.trim())
            entry.categories?.forEach { category ->
                addCategory(category.term)
            }
            channelFactory.setImageFromContent(entry.content)
            channelFactory.setImageFromContent(entry.summary)
            image(entry.mediaThumbnail?.url)
            image(entry.mediaContent?.url)
            channelFactory.youtubeChannelDataBuilder.channelId(entry.youtubeChannelId)
            with(channelFactory.youtubeItemDataBuilder) {
                videoId(entry.youtubeVideoId)
                title(entry.mediaGroup?.title)
                description(entry.mediaGroup?.mediaDescription)
                videoUrl(entry.mediaGroup?.mediaContent?.url)
                thumbnailUrl(entry.mediaGroup?.mediaThumbnail?.url)
                likesCount(entry.mediaGroup?.mediaCommunity?.mediaStarRating?.count?.toIntOrNull())
                viewsCount(entry.mediaGroup?.mediaCommunity?.mediaStatistics?.views?.toIntOrNull())
            }
        }
        channelFactory.buildArticle()
    }
    return channelFactory.build()
}

private fun AtomLinkEntity.generateLink(baseFeedUrl: String?): String? {
    val rel = this.rel
    val href = this.href
    if (
        rel != AtomKeyword.LINK_EDIT.value &&
        rel != AtomKeyword.LINK_SELF.value &&
        rel != AtomKeyword.LINK_REL_ENCLOSURE.value &&
        rel != AtomKeyword.LINK_REL_REPLIES.value
    ) {
        return if (
            baseFeedUrl != null &&
            rel == AtomKeyword.LINK_REL_ALTERNATE.value &&
            href?.startsWith("/") == true
        ) {
            baseFeedUrl + href
        } else {
            href
        }
    }
    return null
}
