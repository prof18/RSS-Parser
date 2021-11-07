package com.prof.rssparser.utils

internal sealed class RSSKeyword(val value: String) {
    object Title: RSSKeyword("title")
    object Image: RSSKeyword("image")
    object Link: RSSKeyword("link")

    object Channel {
        object Channel: RSSKeyword("channel")
        object UpdatePeriod: RSSKeyword("sy:updatePeriod")
        object LastBuildDate: RSSKeyword("lastBuildDate")
    }

    object Item {
        object Item: RSSKeyword("item")
        object Author: RSSKeyword("dc:creator")
        object Category: RSSKeyword("category")
        object Thumbnail: RSSKeyword("media:thumbnail")
        object MediaContent: RSSKeyword("media:content")
        object Enclosure: RSSKeyword("enclosure")
        object Description: RSSKeyword("description")
        object Content: RSSKeyword("content:encoded")
        object PubDate: RSSKeyword("pubDate")
        object Time: RSSKeyword("time")
        object URL: RSSKeyword("url")
        object Type: RSSKeyword("type")
        object GUID: RSSKeyword("guid")
        object Source: RSSKeyword("source")
        object HREF: RSSKeyword("href")

        object News {
            object Image: RSSKeyword("News:Image")
        }

        object Itunes {
            object Image: RSSKeyword("itunes:image")
        }
    }
}

