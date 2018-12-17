package com.prof.rssparser.enginecoroutine

import com.prof.rssparser.core.CoreXMLFetcher
import com.prof.rssparser.core.CoreXMLParser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

object CoroutineEngine {

    @Throws(Exception::class)
    suspend fun fetchXML(url: String) =
            withContext(Dispatchers.IO) {
                return@withContext CoreXMLFetcher.fetchXML(url)
            }

    @Throws(Exception::class)
    suspend fun parseXML(xml: Deferred<String>) =
            withContext(Dispatchers.IO) {
                return@withContext CoreXMLParser.parseXML(xml.await())
            }
}

