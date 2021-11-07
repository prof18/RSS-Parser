package com.prof.rssparser.utils

import org.xmlpull.v1.XmlPullParser

internal fun XmlPullParser.contains(key: RSSKeyword): Boolean {
    return this.name.equals(key.value, ignoreCase = true)
}

internal fun XmlPullParser.nextTrimmedText(): String? = this.nextText().trim()

internal fun XmlPullParser.attributeValue(key: RSSKeyword): String? {
    return this.getAttributeValue(null, key.value)
}
