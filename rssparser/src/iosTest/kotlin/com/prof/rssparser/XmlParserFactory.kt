package com.prof.rssparser

internal actual object XmlParserFactory {
    actual fun createXmlParser(): XmlParser = IosXmlParser()
}