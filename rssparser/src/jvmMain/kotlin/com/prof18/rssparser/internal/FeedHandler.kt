package com.prof18.rssparser.internal

import com.prof18.rssparser.model.RssChannel
import org.xml.sax.Attributes

internal interface FeedHandler {
    fun onStartRssElement(
        qName: String?,
        attributes: Attributes?,
    )

    fun endElement(
        qName: String?,
        text: String,
    )

    fun buildRssChannel(): RssChannel

    /**
     * Determines if the text builder should be cleared when starting a new element.
     * Returns false for HTML tags within content to handle mismatched tag cases.
     * The text builder is cleared only for known RSS/Atom/RDF tags at the starting of the parsing
     */
    fun shouldClearTextBuilder(qName: String?): Boolean
}
