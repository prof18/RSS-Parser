package com.prof18.rssparser

import com.prof18.rssparser.internal.XmlParser

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object XmlParserFactory {
    fun createXmlParser(): XmlParser
}
