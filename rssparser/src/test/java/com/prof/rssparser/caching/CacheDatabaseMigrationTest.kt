package com.prof.rssparser.caching

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CacheDatabaseMigrationTest {

    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        CacheDatabase::class.java,
        emptyList(),
        FrameworkSQLiteOpenHelperFactory(),
    )

    @Test
    fun migrate1To2() = runTest {
        val urlFeedHashCode = 123456
        val date = 123456L
        val version = 40003
        helper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                """
                INSERT INTO feeds 
                VALUES ($urlFeedHashCode, x'0123AB', $date, $version)
            """.trimIndent()
            )

            close()
        }

        helper.runMigrationsAndValidate(TEST_DB, 2, true)

        val defaultCharSet = Charsets.UTF_8.toString()
        val feed = getMigratedRoomDatabase().cachedFeedDao().getCachedFeed(urlFeedHashCode, defaultCharSet)

        assertEquals(feed!!.urlHash, urlFeedHashCode)
        assertEquals(feed.cachedDate, date)
        assertEquals(feed.libraryVersion, version)
        assertEquals(feed.charset, defaultCharSet)
        assertTrue(feed.byteArray.isNotEmpty())
    }

    @Test
    fun migrate2To3() = runTest {
        val urlFeedHashCode = 123456
        val date = 123456L
        val version = 40003
        val defaultCharSet = Charsets.UTF_8.toString()

        helper.createDatabase(TEST_DB, 2).apply {
            execSQL(
                """
                INSERT INTO feeds 
                VALUES ($urlFeedHashCode, x'0123AB', $date, $version, "$defaultCharSet")
            """.trimIndent()
            )

            close()
        }

        helper.runMigrationsAndValidate(TEST_DB, 3, true)

        val feed = getMigratedRoomDatabase().cachedFeedDao().getCachedFeed(urlFeedHashCode, defaultCharSet)

        assertEquals(feed!!.urlHash, urlFeedHashCode)
        assertEquals(feed.cachedDate, date)
        assertEquals(feed.libraryVersion, version)
        assertEquals(feed.charset, defaultCharSet)
        assertTrue(feed.byteArray.isNotEmpty())
    }

    @Test
    fun migrate2To3WithNullValue() = runTest {
        val urlFeedHashCode = 123456
        val date = 123456L
        val version = 40003
        val defaultCharSet = "null"

        helper.createDatabase(TEST_DB, 2).apply {
            execSQL(
                """
                INSERT INTO feeds 
                VALUES ($urlFeedHashCode, x'0123AB', $date, $version, "$defaultCharSet")
            """.trimIndent()
            )

            close()
        }

        helper.runMigrationsAndValidate(TEST_DB, 3, true)

        val feed = getMigratedRoomDatabase().cachedFeedDao().getCachedFeed(urlFeedHashCode, defaultCharSet)

        assertEquals(feed!!.urlHash, urlFeedHashCode)
        assertEquals(feed.cachedDate, date)
        assertEquals(feed.libraryVersion, version)
        assertEquals(feed.charset, defaultCharSet)
        assertTrue(feed.byteArray.isNotEmpty())
    }

    private fun getMigratedRoomDatabase(): CacheDatabase {
        val database: CacheDatabase = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CacheDatabase::class.java, TEST_DB
        ).build()
        helper.closeWhenFinished(database)
        return database
    }
}
