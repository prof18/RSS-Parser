package com.prof.rssparser

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ParserTest {

    @Test
    fun `cacheManager is null if no context provided`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `cacheManager is null is not expiration date provided`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `getChannel returns data`() = runBlocking {
        val parser = Parser.Builder()
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        val channel = parser.getChannel("https://feedurl.com")
        assertNotNull(channel)
    }

    @Test
    fun `getChannel returns data from net and cache data`() = runBlocking {
        val parser = Parser.Builder()
                .context(ApplicationProvider.getApplicationContext())
                .cacheExpirationMillis(ChannelFactory.getOneDayCacheDuration())
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        val cacheManagerSpy = spy(parser.cacheManager)
        parser.cacheManager = cacheManagerSpy

        val url = "https://feedurl.com"
        val channel = parser.getChannel(url)

        // Verify that the channel has been cached. In this way we now that the net value is returned
        verify(cacheManagerSpy, times(1))?.cacheFeed(any(), any(), any(), any())

        // Verify that returns cached data!
        assertNotNull(channel)
    }

    @Test
    fun `getChannel returns cached data`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `getChannel throws execption when network issue`() {
        // TODO: create an error interceptor
        TODO("Not yet implemented")
    }
}