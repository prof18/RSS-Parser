package com.prof18.rssparser.internal

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

internal fun XmlPullParser.nextTrimmedText(): String? {
    val startDepth = this.depth
    return try {
        this.nextText()?.trim()
    } catch (exception: XmlPullParserException) {
        // Only a child tag is recovered here: nextText() stopped on it, one level deeper. An error
        // in the text itself (a bare `&`, an HTML entity like `&auml;`) leaves the depth unchanged
        // and has to reach the caller, which parses the feed again with the XML escaped. Recovering
        // from it would drop the text before the error and read on past the closing tag.
        if (this.depth <= startDepth) throw exception

        // Handle malformed HTML content (e.g., mismatched tag cases like <em>...</EM>) or HTML tags in content
        // When nextText() throws, there are nested tags, so we manually parse and collect all text
        // Note: nextText() may have consumed some text before throwing, so we start fresh from current position

        val result = StringBuilder()
        var depth = 0
        var eventType = this.eventType

        // Process from current position until we find the closing tag at depth 0
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.TEXT, XmlPullParser.CDSECT -> {
                    result.append(this.text)
                }
                XmlPullParser.START_TAG -> {
                    depth++
                }
                XmlPullParser.END_TAG -> {
                    if (depth == 0) {
                        // We've reached the end tag of the original element
                        break
                    }
                    depth--
                }
            }
            eventType = this.next()
        }
        result.toString().trim().takeIf { it.isNotEmpty() }
    }
}

internal fun XmlPullParser.contains(key: RssKeyword): Boolean {
    return this.name.equals(key.value, ignoreCase = true)
}

internal fun XmlPullParser.attributeValue(key: RssKeyword): String? {
    return this.getAttributeValue(null, key.value)?.trim()
}

internal fun XmlPullParser.contains(key: AtomKeyword): Boolean {
    return this.name.equals(key.value, ignoreCase = true)
}

internal fun XmlPullParser.contains(key: RdfKeyword): Boolean {
    return this.name.equals(key.value, ignoreCase = true)
}

internal fun XmlPullParser.attributeValue(key: AtomKeyword): String? {
    return this.getAttributeValue(null, key.value)?.trim()
}
