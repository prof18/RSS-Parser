package com.prof.rssparser.caching

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.prof.rssparser.ChannelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
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

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var database: CacheDatabase
    private lateinit var cacheManager: CacheManager

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CacheDatabase::class.java)
                .allowMainThreadQueries()
                .setTransactionExecutor(testDispatcher.asExecutor())
                .setQueryExecutor(testDispatcher.asExecutor())
                .build()
        cacheManager = CacheManager(database, ChannelFactory.getOneDayCacheDuration(), coroutineDispatcher = testDispatcher)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `cacheFeed cache data`() = testScope.runBlockingTest {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel)

        val channelCached = database.cachedFeedDao().getCachedFeed(url.hashCode())
        assertNotNull(channelCached)
    }

    @Test
    fun `getCachedFeed feed returns correct data`() = testScope.runBlockingTest {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel)

        val cachedChannel = cacheManager.getCachedFeed(url)
        assertEquals(channel, cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when url is incorrect`() = testScope.runBlockingTest {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel)

        val badUrl = "http://www.badurl.com"
        val cachedChannel = cacheManager.getCachedFeed(badUrl)
        assertNull(cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when new library version`() = testScope.runBlockingTest {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        // Just an old version to make sure that the test won't be broken in the future
        cacheManager.cacheFeed(url, channel, libraryVersion = 10000)

        val cachedChannel = cacheManager.getCachedFeed(url)
        assertNull(cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when cache is expired`() = testScope.runBlockingTest {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel, cachedDate = 1L)

        val cachedChannel = cacheManager.getCachedFeed(url)
        assertNull(cachedChannel)
    }

    @Test
    fun `getCachedFeed returns null when cache is not present`() = testScope.runBlockingTest {
        val cachedChannel = cacheManager.getCachedFeed(ChannelFactory.getLink())
        assertNull(cachedChannel)
    }

    @Test
    fun `flushAllCache works correctly`() = testScope.runBlockingTest {
        // Add data to cache
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel)
        val url2 = "https://www.anotherfeed.com"
        cacheManager.cacheFeed(url2, channel)

        // Flush cache
        cacheManager.flushAllCache()

        // Check cache is empty
        val channel1 = database.cachedFeedDao().getCachedFeed(url.hashCode())
        val channel2 = database.cachedFeedDao().getCachedFeed(url2.hashCode())

        assertNull(channel1)
        assertNull(channel2)
    }

    @Test
    fun `flushCachedFeed works correctly`() = testScope.runBlockingTest {
        // Add data to cache
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel)
        val url2 = "https://www.anotherfeed.com"
        cacheManager.cacheFeed(url2, channel)

        // Flush cache of channel 2
        cacheManager.flushCachedFeed(url2)

        // Check cache is empty
        val channel1 = database.cachedFeedDao().getCachedFeed(url.hashCode())
        val channel2 = database.cachedFeedDao().getCachedFeed(url2.hashCode())

        assertNotNull(channel1)
        assertNull(channel2)
    }

    @Test
    fun `flushCachedFeed when a wrong url is passed`() = testScope.runBlockingTest {
        // Add data to cache
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel)

        // Flush cache of wrong channel
        cacheManager.flushCachedFeed("wrong url")

        val cachedFeed = database.cachedFeedDao().getCachedFeed(url.hashCode())
        assertNotNull(cachedFeed)

    }

    @Test
    fun `getCachedFeed deletes data when cache is expired`() = testScope.runBlockingTest {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        cacheManager.cacheFeed(url, channel, cachedDate = 1L)

        cacheManager.getCachedFeed(url)

        // Check if not present in the db
        val cachedFeed = database.cachedFeedDao().getCachedFeed(url.hashCode())
        assertNull(cachedFeed)
    }

    @Test
    fun `getCachedFeed deletes data when library is updated`() = testScope.runBlockingTest {
        val channel = ChannelFactory.getChannel()
        val url = ChannelFactory.getLink()
        // Just an old version to make sure that the test won't be broken in the future
        cacheManager.cacheFeed(url, channel, libraryVersion = 10000)


        cacheManager.getCachedFeed(url)

        // Check if not present in the db
        val cachedFeed = database.cachedFeedDao().getCachedFeed(url.hashCode())
        assertNull(cachedFeed)
    }
}