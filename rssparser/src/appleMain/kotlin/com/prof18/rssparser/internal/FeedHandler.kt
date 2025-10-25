package com.prof18.rssparser.internal

import com.prof18.rssparser.model.RssChannel

internal interface FeedHandler {
    fun didStartElement(startElement: String, attributes: Map<Any?, *>)
    fun didEndElement(endElement: String, text: String)
    fun buildRssChannel(): RssChannel

    /**
     * Determines if the text builder should be cleared when starting a new element.
     * Returns true for known RSS/Atom/RDF tags, false for HTML tags within content.
     */
    fun shouldClearTextBuilder(qName: String): Boolean
}
