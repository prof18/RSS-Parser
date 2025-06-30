package com.prof18.rssparser

import com.prof18.rssparser.internal.WebXmlParser
import com.prof18.rssparser.internal.XmlParser
import kotlinx.coroutines.test.UnconfinedTestDispatcher

internal actual fun createXmlParser(): XmlParser = WebXmlParser(dispatcher = UnconfinedTestDispatcher())
