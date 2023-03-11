package com.prof.rssparser

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object XmlParserFactory {
    fun createXmlParser(): XmlParser
}