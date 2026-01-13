package com.prof18.rssparser.internal.entity

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName(value = "feed")
internal data class AtomFeedEntity(
    @XmlElement
    val title: String? = null,
    @XmlElement
    val icon: String? = null,
    @XmlElement
    val updated: String? = null,
    @XmlElement
    val subtitle: String? = null,
    @XmlElement
    val links: List<AtomLinkEntity>? = null,
    @XmlElement
    val id: String? = null,
    @XmlElement
    val entries: List<AtomEntryEntity>? = null,
) : FeedEntity

@XmlSerialName(value = "link")
@Serializable
internal data class AtomLinkEntity(
    val rel: String? = null,
    val href: String? = null,
    val type: String? = null,
)

@XmlSerialName(value = "entry")
@Serializable
internal data class AtomEntryEntity(
    @XmlElement
    val title: String? = null,
    @XmlElement
    val published: String? = null,
    @XmlElement
    val updated: String? = null,
    @XmlElement
    val id: String? = null,
    @XmlElement
    val content: String? = null,
    @XmlElement
    val summary: String? = null,
    @XmlElement
    val links: List<AtomLinkEntity>? = null,
    @XmlElement
    val author: AtomAuthorEntity? = null,
    @XmlElement
    val categories: List<AtomCategoryEntity>? = null,
    @XmlElement
    @XmlSerialName(value = "group", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaGroup: MediaGroupEntity? = null,
    @XmlElement
    @XmlSerialName(value = "videoId", prefix = "yt", namespace = "http://www.youtube.com/xml/schemas/2015")
    val youtubeVideoId: String? = null,
    @XmlElement
    @XmlSerialName(value = "channelId", prefix = "yt", namespace = "http://www.youtube.com/xml/schemas/2015")
    val youtubeChannelId: String? = null,
    @XmlElement
    @XmlSerialName(value = "thumbnail", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaThumbnail: MediaThumbnailEntity? = null,
    @XmlElement
    @XmlSerialName(value = "content", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaContent: AtomMediaContentEntity? = null,
)

@XmlSerialName(value = "author")
@Serializable
internal data class AtomAuthorEntity(
    @XmlElement val name: String? = null,
)

@XmlSerialName("category")
@Serializable
internal data class AtomCategoryEntity(
    val term: String? = null,
)

@XmlSerialName(value = "group", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
@Serializable
internal data class MediaGroupEntity(
    @XmlElement
    @XmlSerialName(value = "title", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val title: String? = null,
    @XmlElement
    @XmlSerialName(value = "content", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaContent: AtomMediaContentEntity? = null,
    @XmlElement
    @XmlSerialName(value = "thumbnail", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaThumbnail: MediaThumbnailEntity? = null,
    @XmlElement
    @XmlSerialName(value = "description", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaDescription: String? = null,
    @XmlElement
    @XmlSerialName(value = "community", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaCommunity: MediaCommunityEntity? = null,
)

@XmlSerialName(value = "content", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
@Serializable
internal data class AtomMediaContentEntity(
    val url: String? = null,
)

@XmlSerialName(value = "thumbnail", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
@Serializable
internal data class MediaThumbnailEntity(
    val url: String? = null,
)

@XmlSerialName(value = "community", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
@Serializable
internal data class MediaCommunityEntity(
    @XmlElement
    @XmlSerialName(value = "starRating", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaStarRating: MediaStarRatingEntity? = null,
    @XmlElement
    @XmlSerialName(value = "statistics", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaStatistics: MediaStatisticsEntity? = null,
)

@XmlSerialName(value = "starRating", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
@Serializable
internal data class MediaStarRatingEntity(
    val count: String? = null,
)

@XmlSerialName(value = "statistics", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
@Serializable
internal data class MediaStatisticsEntity(
    @XmlSerialName(value = "views", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val views: String? = null,
)
