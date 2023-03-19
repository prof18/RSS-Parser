package com.prof.rssparser

import kotlinx.coroutines.test.UnconfinedTestDispatcher

internal actual object XmlParserFactory {
    actual fun createXmlParser(): XmlParser = IosXmlParser(dispatcher = UnconfinedTestDispatcher())
}
