package com.prof.rssparser

import androidx.test.core.app.ApplicationProvider
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
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
    fun `getChannel returns data`() = runTest {
        parser = Parser.Builder()
            .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
            .build()

        val channel = parser.getChannel("https://feedurl.com")
        assertNotNull(channel)
    }

    @Test
    fun `getChannel returns data from net and cache data`() = runTest {
        parser = Parser.Builder()
            .context(ApplicationProvider.getApplicationContext())
            .cacheExpirationMillis(ChannelFactory.getOneDayCacheDuration())
            .okHttpClient(ChannelFactory.getOkHttpClientForTesting(ChannelFactory.getFeedString()))
            .build()

        val cacheManagerSpy = spyk(parser.cacheManager!!)
        parser.cacheManager = cacheManagerSpy

        val url = "https://feedurl.com"
        val channel = parser.getChannel(url)

        val channelFromCache = parser.getChannel(url)

        // Verify that the channel has been cached. In this way we now that the net value is returned
        coVerify { cacheManagerSpy.cacheFeed(any(), any(), any(), any(), any()) }

        // Verify that returns cached data!
        assertNotNull(channel)

        assertEquals(channel, channelFromCache)

    }

    @Test(expected = Exception::class)
    fun `getChannel throws exception when network issue`() = runTest {
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
        val slot = slot<Channel>()

        val onTaskCompleted = mockk<OnTaskCompleted>()
        justRun { onTaskCompleted.onError(any()) }
        justRun { onTaskCompleted.onTaskCompleted(any()) }

        parser.onFinish(onTaskCompleted)
        parser.execute("https://www.url.com")

        verify(exactly = 1) { onTaskCompleted.onTaskCompleted(capture(slot)) }
        verify(exactly = 0) { onTaskCompleted.onError(any()) }

        val channel = slot.captured
        assertNotNull(channel)
    }

    @Test
    fun `getChannel triggers onError when network issue`() {
        parser = Parser.Builder()
            .okHttpClient(ChannelFactory.getErrorOkHttpClientForTesting())
            .build()

        val onTaskCompleted = mockk<OnTaskCompleted>()
        parser.onFinish(onTaskCompleted)
        parser.executorService = SameThreadExecutorService()

        parser.execute("https://www.url.com")

        verify(exactly = 1) { onTaskCompleted.onError(any()) }
        verify(exactly = 0) { onTaskCompleted.onTaskCompleted(any()) }
    }

    @Test
    fun `Parsing from string returns data`() = runTest {
        parser = Parser.Builder()
            .build()

        val inputStream = javaClass.getResourceAsStream("/feed-test-source.xml")!!
        val feed = inputStream.bufferedReader().use { it.readText() }

        val channel = parser.parse(feed)

        assertNotNull(channel)
    }
}

