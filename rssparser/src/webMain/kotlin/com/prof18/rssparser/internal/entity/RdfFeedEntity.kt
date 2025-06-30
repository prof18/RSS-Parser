package com.prof18.rssparser.internal.entity

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName(value = "RDF", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", prefix = "rdf")
internal data class RdfFeedEntity(
    @XmlElement val channel: RdfChannelEntity,
    @XmlElement val items: List<RdfItem>? = null,
) : FeedEntity

@XmlSerialName(value = "channel", namespace = "http://purl.org/rss/1.0/")
@Serializable
internal data class RdfChannelEntity(
    @XmlElement
    val title: String? = null,
    @XmlElement
    val link: String? = null,
    @XmlElement
    @XmlSerialName(value = "date", prefix = "dc", namespace = "http://purl.org/dc/elements/1.1/")
    val lastBuildDate: String? = null,
    @XmlElement
    val description: String? = null,
    @XmlElement
    @XmlSerialName(value = "updatePeriod", prefix = "sy", namespace = "http://purl.org/rss/1.0/modules/syndication/")
    val updatePeriod: String? = null,
    @XmlElement
    val image: List<RdfChannelImageEntity>? = null,
)

@XmlSerialName(value = "image")
@Serializable
internal data class RdfChannelImageEntity(
    @XmlSerialName(value = "resource", prefix = "rdf", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
    val link: String? = null,
    @XmlValue
    val imageContent: String? = null,
)

@XmlSerialName(value = "item", namespace = "http://purl.org/rss/1.0/")
@Serializable
internal data class RdfItem(
    @XmlElement
    val title: String? = null,
    @XmlElement
    val link: String? = null,
    @XmlElement
    val description: String? = null,
    @XmlElement
    @XmlSerialName(value = "creator", prefix = "dc", namespace = "http://purl.org/dc/elements/1.1/")
    val creator: String? = null,
    @XmlElement
    @XmlSerialName(value = "subject", prefix = "dc", namespace = "http://purl.org/dc/elements/1.1/")
    val subject: String? = null,
    @XmlElement
    @XmlSerialName(value = "date", prefix = "dc", namespace = "http://purl.org/dc/elements/1.1/")
    val pubDate: String? = null,
)
