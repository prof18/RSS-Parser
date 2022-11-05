package com.prof.rssparser.caching

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.prof.rssparser.ChannelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CacheManagerTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var database: CacheDatabase
    private lateinit var cacheManager: CacheManager
    private var defaultCharSet = Charsets.UTF_8.toString()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CacheDatabase::class.java
        )
            .allowMainThreadQueries()
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
        cacheManager = CacheManager(
            database,
            ChannelFactory.getOneDayCacheDuration(),
            coroutineDispatcher = testDispatcher,
        )
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `cacheFeed cache data`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            charset = defaultCharSet,
        )

        val channelCached = database.cachedFeedDao().getCachedFeed(url.hashCode(), defaultCharSet)
        assertNotNull(channelCached)
    }

    @Test
    fun `getCachedFeed feed returns correct data`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            charset = defaultCharSet,
        )

        val cachedChannel = cacheManager.getCachedFeed(url, defaultCharSet)
        assertEquals(channel, cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when url is incorrect`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            charset = defaultCharSet,
        )

        val badUrl = "http://www.badurl.com"
        val cachedChannel = cacheManager.getCachedFeed(badUrl, defaultCharSet)
        assertNull(cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when new library version`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        // Just an old version to make sure that the test won't be broken in the future
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            libraryVersion = 10000,
            charset = defaultCharSet,
        )

        val cachedChannel = cacheManager.getCachedFeed(url, defaultCharSet)
        assertNull(cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when cache is expired`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            cachedDate = 1L,
            charset = defaultCharSet,
        )

        val cachedChannel = cacheManager.getCachedFeed(url, defaultCharSet)
        assertNull(cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when cache is not present`() = runTest(testDispatcher) {
        val cachedChannel = cacheManager.getCachedFeed(ChannelFactory.getLink(), defaultCharSet)
        assertNull(cachedChannel)
    }

    @Test
    fun `flushAllCache works correctly`() = runTest(testDispatcher) {
        // Add data to cache
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            charset = defaultCharSet,
        )
        val url2 = "https://www.anotherfeed.com"
        cacheManager.cacheFeed(
            url = url2,
            channel = channel,
            charset = defaultCharSet,
        )

        // Flush cache
        cacheManager.flushAllCache()

        // Check cache is empty
        val channel1 = database.cachedFeedDao().getCachedFeed(url.hashCode(), defaultCharSet)
        val channel2 = database.cachedFeedDao().getCachedFeed(url2.hashCode(), defaultCharSet)

        assertNull(channel1)
        assertNull(channel2)
    }

    @Test
    fun `flushCachedFeed works correctly`() = runTest(testDispatcher) {
        // Add data to cache
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            charset = defaultCharSet,
        )
        val url2 = "https://www.anotherfeed.com"
        cacheManager.cacheFeed(
            url = url2,
            channel = channel,
            charset = defaultCharSet,
        )

        // Flush cache of channel 2
        cacheManager.flushCachedFeed(url2)

        // Check cache is empty
        val channel1 = database.cachedFeedDao().getCachedFeed(url.hashCode(), defaultCharSet)
        val channel2 = database.cachedFeedDao().getCachedFeed(url2.hashCode(), defaultCharSet)

        assertNotNull(channel1)
        assertNull(channel2)
    }

    @Test
    fun `flushCachedFeed when a wrong url is passed`() = runTest(testDispatcher) {
        // Add data to cache
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            charset = defaultCharSet,
        )

        // Flush cache of wrong channel
        cacheManager.flushCachedFeed("wrong url")

        val cachedFeed = database.cachedFeedDao().getCachedFeed(url.hashCode(), defaultCharSet)
        assertNotNull(cachedFeed)

    }

    @Test
    fun `getCachedFeed deletes data when cache is expired`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            cachedDate = 1L,
            charset = defaultCharSet,
        )

        cacheManager.getCachedFeed(url, defaultCharSet)

        // Check if not present in the db
        val cachedFeed = database.cachedFeedDao().getCachedFeed(url.hashCode(), defaultCharSet)
        assertNull(cachedFeed)
    }

    @Test
    fun `getCachedFeed deletes data when library is updated`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        // Just an old version to make sure that the test won't be broken in the future
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            libraryVersion = 10000,
            charset = defaultCharSet,
        )

        cacheManager.getCachedFeed(url, defaultCharSet)

        // Check if not present in the db
        val cachedFeed = database.cachedFeedDao().getCachedFeed(url.hashCode(), defaultCharSet)
        assertNull(cachedFeed)
    }

    @Test
    fun `getCachedFeed returns null when charset is different`() = runTest(testDispatcher) {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(
            url = url,
            channel = channel,
            cachedDate = 1L,
            charset = defaultCharSet,
        )

        val cachedChannel = cacheManager.getCachedFeed(url, Charsets.ISO_8859_1.toString())
        assertNull(cachedChannel)
    }
}