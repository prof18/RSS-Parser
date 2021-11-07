//package com.prof.rssparser.caching
//
//import android.os.Build
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.asExecutor
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.TestCoroutineScope
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.After
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner
//import org.robolectric.annotation.Config
//
//@ExperimentalCoroutinesApi
//@RunWith(RobolectricTestRunner::class)
//class CacheManagerTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private val testDispatcher = TestCoroutineDispatcher()
//    private val testScope = TestCoroutineScope(testDispatcher)
//
//    private lateinit var database: CacheDatabase
//    private lateinit var cacheManager: CacheManager
//
//    @Before
//    fun initDb() {
//        database = Room.inMemoryDatabaseBuilder(
//                ApplicationProvider.getApplicationContext(),
//                CacheDatabase::class.java)
//                .allowMainThreadQueries()
//                .setTransactionExecutor(testDispatcher.asExecutor())
//                .setQueryExecutor(testDispatcher.asExecutor())
//                .build()
//        cacheManager = CacheManager(database, ChannelFactory.getOneDayCacheDuration(), coroutineDispatcher = testDispatcher)
//    }
//
//    @After
//    fun closeDb() {
//        database.close()
//    }
//
//    @Test
//    fun cacheFeed_cache_data() = testScope.runBlockingTest {
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel)
//
//        val channelCached = database.cachedProjectsDao().getCachedFeed(url.hashCode())
//        assertNotNull(channelCached)
//    }
//
//    @Test
//    fun getCachedFeed_feed_returns_correct_data() = testScope.runBlockingTest {
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel)
//
//        val cachedChannel = cacheManager.getCachedFeed(url)
//        assertEquals(channel, cachedChannel)
//    }
//
//    @Test
//    fun `getCachedFeed_feed_returns_null_when_not_present`() = testScope.runBlockingTest {
//        val cachedChannel = cacheManager.getCachedFeed("http://www.url.com")
//        assertNull(cachedChannel)
//    }
//
//    @Test
//    fun `getCachedFeed_returns_null_when_url_is_incorrect`() = testScope.runBlockingTest {
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel)
//
//        val badUrl = "http://www.badurl.com"
//        val cachedChannel = cacheManager.getCachedFeed(badUrl)
//        assertNull(cachedChannel)
//    }
//
//    @Test
//    fun `getCachedFeed_returns_null_when_new_library_version`() = testScope.runBlockingTest {
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        // Just an old version to make sure that the test won't be broken in the future
//        cacheManager.cacheFeed(url, channel, libraryVersion = 10000)
//
//        val cachedChannel = cacheManager.getCachedFeed(url)
//        assertNull(cachedChannel)
//    }
//
//    @Test
//    fun `getCachedFeed_returns_null_when_cache_is_expired`() = testScope.runBlockingTest {
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel, cachedDate = 1L)
//
//        val cachedChannel = cacheManager.getCachedFeed(url)
//        assertNull(cachedChannel)
//    }
//
//    @Test
//    fun `flushAllCache_works_correctly`() = testScope.runBlockingTest {
//        // Add data to cache
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel)
//        val url2 = "https://www.anotherfeed.com"
//        cacheManager.cacheFeed(url2, channel)
//
//        // Flush cache
//        cacheManager.flushAllCache()
//
//        // Check cache is empty
//        val channel1 = database.cachedProjectsDao().getCachedFeed(url.hashCode())
//        val channel2 = database.cachedProjectsDao().getCachedFeed(url2.hashCode())
//
//        assertNull(channel1)
//        assertNull(channel2)
//    }
//
//    @Test
//    fun `flushCachedFeed_works_correctly`() = testScope.runBlockingTest {
//        // Add data to cache
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel)
//        val url2 = "https://www.anotherfeed.com"
//        cacheManager.cacheFeed(url2, channel)
//
//        // Flush cache of channel 2
//        cacheManager.flushCachedFeed(url2)
//
//        // Check cache is empty
//        val channel1 = database.cachedProjectsDao().getCachedFeed(url.hashCode())
//        val channel2 = database.cachedProjectsDao().getCachedFeed(url2.hashCode())
//
//        assertNotNull(channel1)
//        assertNull(channel2)
//    }
//
//    @Test
//    fun `flushCachedFeed_when_a_wrong_url_is_passed`() = testScope.runBlockingTest {
//        // Add data to cache
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel)
//
//        // Flush cache of wrong channel
//        cacheManager.flushCachedFeed("wrong url")
//
//        val cachedFeed = database.cachedProjectsDao().getCachedFeed(url.hashCode())
//        assertNotNull(cachedFeed)
//
//    }
//
//    @Test
//    fun `getCachedFeed_deletes_data_when_cache_is_expired`() = testScope.runBlockingTest {
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        cacheManager.cacheFeed(url, channel, cachedDate = 1L)
//
//        cacheManager.getCachedFeed(url)
//
//        // Check if not present in the db
//        val cachedFeed = database.cachedProjectsDao().getCachedFeed(url.hashCode())
//        assertNull(cachedFeed)
//    }
//
//    @Test
//    fun `getCachedFeed_deletes_data_when_library_is_updated`() = testScope.runBlockingTest {
//        val channel = ChannelFactory.getChannel()
//        val url = ChannelFactory.getLink()
//        // Just an old version to make sure that the test won't be broken in the future
//        cacheManager.cacheFeed(url, channel, libraryVersion = 10000)
//
//
//        cacheManager.getCachedFeed(url)
//
//        // Check if not present in the db
//        val cachedFeed = database.cachedProjectsDao().getCachedFeed(url.hashCode())
//        assertNull(cachedFeed)
//    }
//}