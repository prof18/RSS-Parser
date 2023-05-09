package com.prof.rssparser

import com.prof.rssparser.internal.JvmXmlParser
import com.prof.rssparser.internal.XmlParser
import kotlinx.coroutines.test.UnconfinedTestDispatcher

internal actual object XmlParserFactory {
    actual fun createXmlParser(): XmlParser = JvmXmlParser(dispatcher = UnconfinedTestDispatcher())
}