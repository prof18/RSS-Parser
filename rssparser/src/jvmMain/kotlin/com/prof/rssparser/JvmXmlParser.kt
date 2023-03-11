package com.prof.rssparser

import java.nio.charset.Charset

class JvmXmlParser(
    private val charset: Charset? = null,
): XmlParser {

    //TODO: dispatchers
    override suspend fun parseXML(input: ParserInput): Channel {
        TODO()
    }
}