package com.prof.rssparser

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ParserTest {
    
    private lateinit var parser: Parser
    
    @After
    fun tearDown() {
        parser.cacheManager?.database?.close()
        parser.cacheManager = null
    }

    @Test
    fun `cacheManager is null if no context provided`() {
        parser = Parser.Builder()
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        assertNull(parser.cacheManager)
    }

    @Test
    fun `cacheManager is null if not expiration date provided`() {
        parser = Parser.Builder()
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        assertNull(parser.cacheManager)
    }

    @Test
    fun `cacheManager is null if expiration date is not provided but context is provided`() {
        parser = Parser.Builder()
                .context(ApplicationProvider.getApplicationContext())
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        assertNull(parser.cacheManager)
    }

    @Test
    fun `cacheManager is null if expiration date is provided but context is not provided`() {
        parser = Parser.Builder()
                .cacheExpirationMillis(ChannelFactory.getOneDayCacheDuration())
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        assertNull(parser.cacheManager)
    }

    @Test
    fun `getChannel returns data`() = runBlocking {
        parser = Parser.Builder()
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        val channel = parser.getChannel("https://feedurl.com")
        assertNotNull(channel)
    }

    @Test
    fun `getChannel returns data from net and cache data`() = runBlocking {
        parser = Parser.Builder()
                .context(ApplicationProvider.getApplicationContext())
                .cacheExpirationMillis(ChannelFactory.getOneDayCacheDuration())
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        val cacheManagerSpy = spy(parser.cacheManager)
        parser.cacheManager = cacheManagerSpy

        val url = "https://feedurl.com"
        val channel = parser.getChannel(url)

        val channelFromCache = parser.getChannel(url)

        // Verify that the channel has been cached. In this way we now that the net value is returned
        verify(cacheManagerSpy, times(1))?.cacheFeed(any(), any(), any(), any())

        // Verify that returns cached data!
        assertNotNull(channel)

        assertEquals(channel, channelFromCache)

    }

    @Test(expected = Exception::class)
    fun `getChannel throws exception when network issue`() = runBlockingTest {
        parser = Parser.Builder()
                .okHttpClient(ChannelFactory.getErrorOkHttpClientForTesting())
                .build()

        parser.getChannel("https://www.url.it")
    }

    // Tests for Java compatibility
    @Test
    fun `getChannel triggers onFinish when data are found`() {
        parser = Parser.Builder()
                .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
                .build()

        parser.executorService = SameThreadExecutorService()
        val argumentCaptor = argumentCaptor<Channel>()

        val onTaskCompleted = mock<OnTaskCompleted>()
        parser.onFinish(onTaskCompleted)

        parser.execute("https://www.url.com")

        verify(onTaskCompleted).onTaskCompleted(argumentCaptor.capture())
        val channel = argumentCaptor.firstValue

        assertNotNull(channel)
        verify(onTaskCompleted, times(0)).onError(any())
        verify(onTaskCompleted, times(1)).onTaskCompleted(any())
    }

    @Test
    fun `getChannel triggers onError when network issue`() {
        parser = Parser.Builder()
                .okHttpClient(ChannelFactory.getErrorOkHttpClientForTesting())
                .build()

        val onTaskCompleted = mock<OnTaskCompleted>()
        parser.onFinish(onTaskCompleted)
        parser.executorService = SameThreadExecutorService()

        parser.execute("https://www.url.com")

        verify(onTaskCompleted, times(1)).onError(any())
        verify(onTaskCompleted, times(0)).onTaskCompleted(any())
    }
}

