package com.prof.rssparser.core

import com.prof.rssparser.HTTPException
import com.prof.rssparser.testutils.ChannelFactory
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CoreXMLFetcherTest {

    @Test(expected = HTTPException::class)
    fun `fetchXML throws exception if a 2XX HTTP error code is not returned`() {
        CoreXMLFetcher.fetchXML(
            url = "https://example.com",
            callFactory = ChannelFactory.getErrorOkHttpClientForTesting()
        )
    }

    @Test(expected = HTTPException::class)
    fun `fetchXMLSuspendable throws exception if a 2XX HTTP error code is not returned`() =
        runTest {
            CoreXMLFetcher.fetchXMLSuspendable(
                url = "https://example.com",
                callFactory = ChannelFactory.getErrorOkHttpClientForTesting()
            )
        }
}
