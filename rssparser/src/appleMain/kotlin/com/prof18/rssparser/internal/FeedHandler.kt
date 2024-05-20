package com.prof18.rssparser.internal

import com.prof18.rssparser.model.RssChannel

internal interface FeedHandler {
    fun didStartElement(startElement: String, attributes: Map<Any?, *>)
    fun foundCharacters(characters: String)
    fun didEndElement(endElement: String)
    fun buildRssChannel(): RssChannel
}
