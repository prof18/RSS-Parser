package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import com.prof18.rssparser.internal.XmlFetcher
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.test.Test
import kotlin.test.assertTrue

class RssParserCancellationTest : XmlParserTestExecutor() {

    private val fetchStarted = CompletableDeferred<Unit>()
    private val fetchCancelled = CompletableDeferred<Unit>()

    private val rssParser = RssParser(
        xmlFetcher = object : XmlFetcher {
            override suspend fun fetchXml(url: String): ParserInput = waitForCancellation()

            override suspend fun fetchXmlAsString(url: String): String = waitForCancellation()

            private suspend fun waitForCancellation(): Nothing {
                fetchStarted.complete(Unit)
                try {
                    awaitCancellation()
                } finally {
                    fetchCancelled.complete(Unit)
                }
            }
        },
        xmlParser = createXmlParser(),
    )

    @Test
    fun cancellingTheCallerCancelsTheFetchOfGetRssChannel() = runTest {
        // Real time and a scope of its own: a fetch that ignores the cancellation never lets
        // the caller finish, and the test has to report that instead of waiting for it.
        withContext(Dispatchers.Default) {
            val caller = CoroutineScope(Dispatchers.Default).launch { rssParser.getRssChannel("feed-url") }
            withTimeout(TIMEOUT_MILLIS) { fetchStarted.await() }

            val callerFinished = withTimeoutOrNull(TIMEOUT_MILLIS) { caller.cancelAndJoin() } != null

            assertTrue(callerFinished, "The cancelled caller is still waiting for the fetch")
            assertTrue(fetchCancelled.isCompleted)
        }
    }

    private companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}
