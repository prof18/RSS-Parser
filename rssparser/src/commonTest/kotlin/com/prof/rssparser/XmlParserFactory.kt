package com.prof.rssparser

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object XmlParserFactory {
    fun createXmlParser(): XmlParser
}