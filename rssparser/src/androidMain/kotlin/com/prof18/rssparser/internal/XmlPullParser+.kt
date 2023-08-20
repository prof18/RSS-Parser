package com.prof18.rssparser.internal

import org.xmlpull.v1.XmlPullParser

internal fun XmlPullParser.nextTrimmedText(): String? = this.nextText()?.trim()

internal fun XmlPullParser.contains(key: RssKeyword): Boolean {
    return this.name.equals(key.value, ignoreCase = true)
}

internal fun XmlPullParser.attributeValue(key: RssKeyword): String? {
    return this.getAttributeValue(null, key.value)?.trim()
}

internal fun XmlPullParser.contains(key: AtomKeyword): Boolean {
    return this.name.equals(key.value, ignoreCase = true)
}

internal fun XmlPullParser.attributeValue(key: AtomKeyword): String? {
    return this.getAttributeValue(null, key.value)?.trim()
}
