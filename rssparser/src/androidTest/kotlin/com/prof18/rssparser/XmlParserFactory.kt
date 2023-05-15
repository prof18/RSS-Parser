package com.prof18.rssparser

import com.prof18.rssparser.internal.AndroidXmlParser
import com.prof18.rssparser.internal.XmlParser
import kotlinx.coroutines.test.UnconfinedTestDispatcher

internal actual object XmlParserFactory {
    actual fun createXmlParser(): XmlParser = AndroidXmlParser(dispatcher = UnconfinedTestDispatcher())
}
