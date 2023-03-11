package com.prof.rssparser

actual object XmlParserFactory {
    actual fun createXmlParser(): XmlParser = IosXmlParser()
}