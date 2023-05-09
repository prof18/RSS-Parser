package com.prof.rssparser

import com.prof.rssparser.internal.XmlParser

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object XmlParserFactory {
    fun createXmlParser(): XmlParser
}