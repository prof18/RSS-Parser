/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof.rssparser

import com.prof.rssparser.engine.XMLFetcher
import com.prof.rssparser.engine.XMLParser
import com.prof.rssparser.enginecoroutine.CoroutineEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.Executors


class Parser {

    private lateinit var onComplete: OnTaskCompleted

    fun onFinish(onComplete: OnTaskCompleted) {
        this.onComplete = onComplete
    }

    fun execute(url: String) {
        val service = Executors.newFixedThreadPool(2)
        val f1 = service.submit<String>(XMLFetcher(url))
        try {
            val rssFeed = f1.get()
            val f2 = service.submit(XMLParser(rssFeed))
            onComplete.onTaskCompleted(f2.get())
        } catch (e: Exception) {
            onComplete.onError(e)
        } finally {
            service.shutdown()
        }
    }

    @Throws(Exception::class)
    suspend fun getArticles(url: String) =
            withContext(Dispatchers.IO) {
                val xml = async { CoroutineEngine.fetchXML(url) }
                return@withContext CoroutineEngine.parseXML(xml)
            }


}

