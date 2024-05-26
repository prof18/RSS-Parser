package com.prof18.rssparser

import com.prof18.rssparser.internal.XmlParser

internal expect object XmlParserFactory {
    fun createXmlParser(): XmlParser
}
