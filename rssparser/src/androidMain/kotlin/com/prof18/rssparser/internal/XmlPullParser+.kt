package com.prof18.rssparser.internal

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

internal fun XmlPullParser.nextTrimmedText(): String? {
    return try {
        this.nextText()?.trim()
    } catch (_: XmlPullParserException) {
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
