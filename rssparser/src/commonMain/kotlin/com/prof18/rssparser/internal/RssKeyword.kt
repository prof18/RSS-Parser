package com.prof18.rssparser.internal

internal sealed class RssKeyword(val value: String) {
    object Rss: RssKeyword("rss")
    object Title : RssKeyword("title")
    object Image : RssKeyword("image")
    object Link : RssKeyword("link")
    object Href : RssKeyword("href")
    object Url : RssKeyword("url")
    object Description : RssKeyword("description")

    object Itunes {
        object Author : RssKeyword("itunes:author")
        object Duration : RssKeyword("itunes:duration")
        object Keywords : RssKeyword("itunes:keywords")
        object Image : RssKeyword("itunes:image")
        object Explicit : RssKeyword("itunes:explicit")
        object Subtitle : RssKeyword("itunes:subtitle")
        object Summary : RssKeyword("itunes:summary")
    }

    object Channel {
        object Channel : RssKeyword("channel")
        object UpdatePeriod : RssKeyword("sy:updatePeriod")
        object LastBuildDate : RssKeyword("lastBuildDate")

        object Itunes {
            object Category : RssKeyword("itunes:category")
            object Owner : RssKeyword("itunes:owner")
            object OwnerName : RssKeyword("itunes:name")
            object OwnerEmail : RssKeyword("itunes:email")
            object Type : RssKeyword("itunes:type")
            object NewFeedUrl : RssKeyword("itunes:new-feed-url")
            object Text : RssKeyword("text")
        }
    }

    object Item {
        object Item : RssKeyword("item")
        object Author : RssKeyword("dc:creator")
        object Category : RssKeyword("category")
        object Thumbnail : RssKeyword("media:thumbnail")
        object MediaContent : RssKeyword("media:content")
        object Enclosure : RssKeyword("enclosure")
        object Content : RssKeyword("content:encoded")
        object PubDate : RssKeyword("pubDate")
        object Time : RssKeyword("time")
        object Type : RssKeyword("type")
        object Guid : RssKeyword("guid")
        object Source : RssKeyword("source")
        object Comments : RssKeyword("comments")
        object Thumb: RssKeyword("thumb")

        object News {
            object Image : RssKeyword("News:Image")
        }

        object Itunes {
            object Episode : RssKeyword("itunes:episode")
            object Season : RssKeyword("itunes:season")
            object EpisodeType : RssKeyword("itunes:episodeType")
        }
    }
}
