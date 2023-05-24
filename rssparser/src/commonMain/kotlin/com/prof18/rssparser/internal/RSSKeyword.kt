package com.prof18.rssparser.internal

internal sealed class RSSKeyword(val value: String) {
    object Rss: RSSKeyword("rss")object Title : RSSKeyword("title")
    object Image : RSSKeyword("image")
    object Link : RSSKeyword("link")
    object Href : RSSKeyword("href")
    object Url : RSSKeyword("url")
    object Description : RSSKeyword("description")

    object Itunes {
        object Author : RSSKeyword("itunes:author")
        object Duration : RSSKeyword("itunes:duration")
        object Keywords : RSSKeyword("itunes:keywords")
        object Image : RSSKeyword("itunes:image")
        object Explicit : RSSKeyword("itunes:explicit")
        object Subtitle : RSSKeyword("itunes:subtitle")
        object Summary : RSSKeyword("itunes:summary")
    }

    object Channel {
        object Channel : RSSKeyword("channel")
        object UpdatePeriod : RSSKeyword("sy:updatePeriod")
        object LastBuildDate : RSSKeyword("lastBuildDate")

        object Itunes {
            object Category : RSSKeyword("itunes:category")
            object Owner : RSSKeyword("itunes:owner")
            object OwnerName : RSSKeyword("itunes:name")
            object OwnerEmail : RSSKeyword("itunes:email")
            object Type : RSSKeyword("itunes:type")
            object NewFeedUrl : RSSKeyword("itunes:new-feed-url")
            object Text : RSSKeyword("text")
        }
    }

    object Item {
        object Item : RSSKeyword("item")
        object Author : RSSKeyword("dc:creator")
        object Category : RSSKeyword("category")
        object Thumbnail : RSSKeyword("media:thumbnail")
        object MediaContent : RSSKeyword("media:content")
        object Enclosure : RSSKeyword("enclosure")
        object Content : RSSKeyword("content:encoded")
        object PubDate : RSSKeyword("pubDate")
        object Time : RSSKeyword("time")
        object Type : RSSKeyword("type")
        object Guid : RSSKeyword("guid")
        object Source : RSSKeyword("source")
        object Comments : RSSKeyword("comments")

        object News {
            object Image : RSSKeyword("News:Image")
        }

        object Itunes {
            object Episode : RSSKeyword("itunes:episode")
            object Season : RSSKeyword("itunes:season")
            object EpisodeType : RSSKeyword("itunes:episodeType")
        }
    }
}