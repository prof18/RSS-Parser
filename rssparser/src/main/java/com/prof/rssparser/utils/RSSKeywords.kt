/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof.rssparser.utils

internal object RSSKeywords {

    // channel
    const val RSS_CHANNEL = "channel"
    const val RSS_CHANNEL_IMAGE = "image"
    const val RSS_CHANNEL_UPDATE_PERIOD = "sy:updatePeriod"
    const val RSS_CHANNEL_LAST_BUILD_DATE = "lastBuildDate"
    // channel itunes
    const val RSS_CHANNEL_ITUNES_AUTHOR = "itunes:author"
    const val RSS_CHANNEL_ITUNES_DURATION = "itunes:duration"
    const val RSS_CHANNEL_ITUNES_KEYWORDS = "itunes:keywords"
    const val RSS_CHANNEL_ITUNES_CATEGORY = "itunes:category"
    const val RSS_CHANNEL_ITUNES_IMAGE = "itunes:image"
    const val RSS_CHANNEL_ITUNES_EXPLICIT = "itunes:explicit"
    const val RSS_CHANNEL_ITUNES_OWNER = "itunes:owner"
    const val RSS_CHANNEL_ITUNES_OWNER_NAME = "itunes:name"
    const val RSS_CHANNEL_ITUNES_OWNER_EMAIL = "itunes:email"
    const val RSS_CHANNEL_ITUNES_SUBTITLE = "itunes:subtitle"
    const val RSS_CHANNEL_ITUNES_TYPE = "itunes:type"
    const val RSS_CHANNEL_ITUNES_NEW_FEED_URL = "itunes:new-feed-url"

    // article
    const val RSS_ITEM = "item"
    const val RSS_ITEM_TITLE = "title"
    const val RSS_ITEM_LINK = "link"
    const val RSS_ITEM_AUTHOR = "dc:creator"
    const val RSS_ITEM_CATEGORY = "category"
    const val RSS_ITEM_THUMBNAIL = "media:thumbnail"
    const val RSS_ITEM_MEDIA_CONTENT = "media:content"
    const val RSS_ITEM_ENCLOSURE = "enclosure"
    const val RSS_ITEM_DESCRIPTION = "description"
    const val RSS_ITEM_CONTENT = "content:encoded"
    const val RSS_ITEM_PUB_DATE = "pubDate"
    const val RSS_ITEM_TIME = "time"
    const val RSS_ITEM_URL = "url"
    const val RSS_ITEM_TYPE = "type"
    const val RSS_ITEM_GUID = "guid"
    const val RSS_ITEM_SOURCE = "source"
    const val RSS_ITEM_HREF = "href"
    const val RSS_ITEM_IMAGE_NEWS = "News:Image"
    // article itunes
    const val RSS_ITEM_ITUNES_IMAGE = "itunes:image"
    const val RSS_ITEM_ITUNES_DURATION = "itunes:duration"
    const val RSS_ITEM_ITUNES_EXPLICIT = "itunes:explicit"
    const val RSS_ITEM_ITUNES_KEYWORDS = "itunes:keywords"
    const val RSS_ITEM_ITUNES_SUBTITLE = "itunes:subtitle"
    const val RSS_ITEM_ITUNES_EPISODE = "itunes:episode"
    const val RSS_ITEM_ITUNES_EPISODE_TYPE = "itunes:episodeType"
    const val RSS_ITEM_ITUNES_AUTHOR = "itunes:author"
}