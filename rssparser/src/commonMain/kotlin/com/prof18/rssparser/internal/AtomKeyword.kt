package com.prof18.rssparser.internal

internal sealed class AtomKeyword(val value: String) {
    data object Atom : AtomKeyword("feed")
    data object Title : AtomKeyword("title")
    data object Icon : AtomKeyword("icon")
    data object Link : AtomKeyword("link") {
        data object Href : AtomKeyword("href")
        data object Rel : AtomKeyword("rel") {
            data object Alternate : AtomKeyword("alternate")
            data object Enclosure : AtomKeyword("enclosure")
        }
        data object Edit : AtomKeyword("edit")
        data object Self : AtomKeyword("self")
    }

    data object Subtitle : AtomKeyword("subtitle")
    data object Updated : AtomKeyword("updated")
    data object Entry {
        data object Item : AtomKeyword("entry")
        data object Guid : AtomKeyword("id")
        data object Content : AtomKeyword("content")
        data object Published : AtomKeyword("published")
        data object Category : AtomKeyword("category")
        data object Term : AtomKeyword("term")
        data object Description : AtomKeyword("summary")
        data object Author : AtomKeyword("name")
        data object Email : AtomKeyword("email")
    }

    data object Youtube {
        data object ChannelId : AtomKeyword("yt:channelId")
        data object VideoId : AtomKeyword("yt:videoId")
        data object MediaGroup : AtomKeyword("media:group") {
            data object MediaTitle : AtomKeyword("media:title")
            data object MediaContent : AtomKeyword("media:content") {
                data object Url : AtomKeyword("url")
            }
            data object MediaThumbnail : AtomKeyword("media:thumbnail") {
                data object Url : AtomKeyword("url")
            }
            data object MediaDescription : AtomKeyword("media:description")
            data object MediaCommunity : AtomKeyword("media:community") {
                data object MediaStarRating : AtomKeyword("media:starRating") {
                    data object Count : AtomKeyword("count")
                }
                data object MediaStatistics : AtomKeyword("media:statistics") {
                    data object Views : AtomKeyword("views")
                }
            }
        }
    }
}
