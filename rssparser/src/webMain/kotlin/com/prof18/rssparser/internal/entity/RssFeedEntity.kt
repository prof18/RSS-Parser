package com.prof18.rssparser.internal.entity

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@XmlSerialName(value = "rss")
@Serializable
internal data class RssFeedEntity(
    @XmlElement
    val channel: RssChannelEntity,
) : FeedEntity

@XmlSerialName(value = "channel")
@Serializable
internal data class RssChannelEntity(
    @XmlElement
    val title: String? = null,
    @XmlElement
    val link: String? = null,
    @XmlElement
    val description: String? = null,
    @XmlElement
    val lastBuildDate: String? = null,
    @XmlElement
    val items: List<RssItemEntity>? = null,
    @XmlElement
    val image: ImageEntity? = null,
    @XmlElement
    @XmlSerialName(value = "updatePeriod", prefix = "sy", namespace = "http://purl.org/rss/1.0/modules/syndication/")
    val updatePeriod: String? = null,
    @XmlElement
    @XmlSerialName(value = "summary", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesSummary: String? = null,
    @XmlElement
    @XmlSerialName(value = "author", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesAuthor: String? = null,
    @XmlElement
    @XmlSerialName(value = "duration", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesDuration: String? = null,
    @XmlElement
    @XmlSerialName(value = "explicit", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesExplicit: String? = null,
    @XmlElement
    @XmlSerialName(value = "keywords", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesKeywords: String? = null,
    @XmlElement
    @XmlSerialName(value = "category", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesCategories: List<ItunesCategoryEntity>? = null,
    @XmlElement
    @XmlSerialName(value = "image", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesImage: ItunesImageEntity? = null,
    @XmlElement
    @XmlSerialName(value = "owner", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesOwner: ItunesOwnerEntity? = null,
    @XmlElement
    @XmlSerialName(value = "subtitle", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesSubtitle: String? = null,
    @XmlElement
    @XmlSerialName(value = "type", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesType: String? = null,
    @XmlElement
    @XmlSerialName(value = "new-feed-url", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val newFeedUrl: String? = null,
)

@XmlSerialName(value = "item")
@Serializable
internal data class RssItemEntity(
    @XmlElement
    val title: String? = null,
    @XmlElement
    val description: String? = null,
    @XmlElement
    val link: String? = null,
    @XmlElement
    val pubDate: String? = null,
    @XmlElement
    val comments: String? = null,
    @XmlElement
    val categories: List<RssCategoryEntity>? = null,
    @XmlElement
    val guid: String? = null,
    @XmlElement
    val enclosures: List<EnclosureEntity>? = null,
    @XmlElement
    val source: SourceEntity? = null,
    @XmlElement
    val thumb: String? = null,
    @XmlElement
    @XmlSerialName(value = "creator", prefix = "dc", namespace = "http://purl.org/dc/elements/1.1/")
    val author: String? = null,
    @XmlElement
    @XmlSerialName(value = "encoded", prefix = "content", namespace = "http://purl.org/rss/1.0/modules/content/")
    val contentEncoded: RssContentEncodedEntity? = null,
    @XmlElement
    @XmlSerialName(value = "content", prefix = "media", namespace = "https://search.yahoo.com/mrss/")
    val mediaContent: RssMediaContentEntity? = null,
    @XmlElement
    @XmlSerialName(value = "content", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaContent2: RssMediaContentEntity? = null,
    @XmlElement
    @XmlSerialName(value = "image", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesImage: ItunesImageEntity? = null,
    @XmlElement
    @XmlSerialName(value = "duration", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesDuration: String? = null,
    @XmlElement
    @XmlSerialName(value = "explicit", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesExplicit: String? = null,
    @XmlElement
    @XmlSerialName(value = "keywords", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesKeywords: String? = null,
    @XmlElement
    @XmlSerialName(value = "subtitle", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesSubtitle: String? = null,
    @XmlElement
    @XmlSerialName(value = "episode", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesEpisode: String? = null,
    @XmlElement
    @XmlSerialName(value = "episodeType", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesEpisodeType: String? = null,
    @XmlElement
    @XmlSerialName(value = "author", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesAuthor: String? = null,
    @XmlElement
    @XmlSerialName(value = "season", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesSeason: String? = null,
    @XmlElement
    @XmlSerialName(value = "summary", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    val itunesSummary: String? = null,
    @XmlElement
    @XmlSerialName(
        value = "Image",
        namespace = "https://www.bing.com:443/news/search?q=madrid&format=rss",
        prefix = "News"
    )
    val newsImage: NewsImageEntity? = null,
    @XmlElement
    @XmlSerialName(value = "date", prefix = "dc", namespace = "http://purl.org/dc/elements/1.1/")
    val dcDate: String? = null,
    @XmlElement
    val image: ImageEntity? = null,
    @XmlElement
    @XmlSerialName(value = "thumbnail", prefix = "media", namespace = "http://search.yahoo.com/mrss/")
    val mediaThumbnail: RssMediaThumbnailEntity? = null,
)

@XmlSerialName(value = "category")
@Serializable
internal data class RssCategoryEntity(
    @XmlValue val name: String? = null,
)

@XmlSerialName(value = "content", prefix = "media", namespace = "https://search.yahoo.com/mrss/")
@Serializable
internal data class RssMediaContentEntity(
    val url: String? = null,
    val name: String? = null,
    val medium: String? = null,
    val width: String? = null,
    val height: String? = null,
    val type: String? = null,
)

@XmlSerialName(value = "encoded", prefix = "content", namespace = "http://purl.org/rss/1.0/modules/content/")
@Serializable
internal data class RssContentEncodedEntity(
    @XmlValue val value: String? = null,
)

@XmlSerialName(value = "owner", prefix = "itunes", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd")
@Serializable
internal data class ItunesOwnerEntity(
    @XmlElement val name: String? = null,
    @XmlElement val email: String? = null,
)

@XmlSerialName(value = "image")
@Serializable
internal data class ImageEntity(
    @XmlElement val url: String? = null,
    @XmlElement val title: String? = null,
    @XmlElement val link: String? = null,
    @XmlElement val description: String? = null,
)

@XmlSerialName(value = "category", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
@Serializable
internal data class ItunesCategoryEntity(
    val text: String? = null,
    @XmlElement
    @XmlSerialName(value = "category", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
    val subCategories: List<ItunesCategoryEntity>? = null,
)

@XmlSerialName(value = "image", namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
@Serializable
internal data class ItunesImageEntity(
    val href: String? = null,
)

@XmlSerialName(value = "enclosure")
@Serializable
internal data class EnclosureEntity(
    val url: String? = null,
    val type: String? = null,
    val length: String? = null,
    @XmlValue val value: String? = null,
)

@XmlSerialName(value = "Image", namespace = "https://www.bing.com:443/news/search?q=madrid&format=rss", prefix = "News")
@Serializable
internal data class NewsImageEntity(
    @XmlValue val link: String? = null,
)

@XmlSerialName(value = "source")
@Serializable
internal data class SourceEntity(
    val url: String? = null,
    @XmlValue val name: String? = null,
)

@XmlSerialName(value = "thumbnail", namespace = "http://search.yahoo.com/mrss/", prefix = "media")
@Serializable
internal data class RssMediaThumbnailEntity(
    val url: String? = null,
)
