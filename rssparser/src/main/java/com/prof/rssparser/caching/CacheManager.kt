package com.prof.rssparser.caching

import android.content.Context
import android.util.Log
import com.prof.rssparser.BuildConfig
import com.prof.rssparser.Channel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@Suppress("BlockingMethodInNonBlockingContext")
/**
 *  The compiler does not know that we are going always to provide a Dispatcher.IO
 *  in the [coroutineDispatcher] so it shows an issue. The [coroutineDispatcher] is used only in
 *  the test, to provide a TestCoroutineDispatcher
 *
 */
class CacheManager(internal val database: CacheDatabase, // internal just for close db during testing
                   private val cacheDurationMillis: Long,
                   private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     *
     * Flush all the cache in memory
     *
     */
    suspend fun flushAllCache() {
        database.cachedProjectsDao().deleteAllFeed()
    }

    /**
     * Flush the [Channel] cached value for the [url] provided
     *
     * @param url The url of the RSS feed.
     */
    suspend fun flushCachedFeed(url: String) {
        database.cachedProjectsDao().deleteFeed(url.hashCode())
    }

    /**
     *
     * Cached the [Channel] provided as parameter. The key is the [url] and the [channel] is saved as bytes
     *
     * N.B. The [coroutineDispatcher] has to be Dispatchers.IO
     *
     * @param url The url of the RSS feed. The hash code of the url is the key.
     * @param channel The [Channel] to cache
     * @param cachedDate The cached date. This param is not used by the client but only for testing
     * @param libraryVersion The version of RSS Parser library. This param is not used by the client but only for testing
     *
     */
    suspend fun cacheFeed(url: String, channel: Channel,
                          cachedDate: Long = System.currentTimeMillis(),
                          libraryVersion: Int = BuildConfig.VERSION_CODE) {
        // The coroutineDispatcher has to be Dispatchers.IO
        withContext(coroutineDispatcher) {
            var objectStream: ObjectOutputStream? = null
            val outputStream = ByteArrayOutputStream()
            try {
                objectStream = ObjectOutputStream(outputStream)

                objectStream.writeObject(channel)
                objectStream.flush()

                val bytes = outputStream.toByteArray()
                val urlHash = url.hashCode()

                val cachedFeed = CachedFeed(
                        urlHash = urlHash,
                        byteArray = bytes,
                        cachedDate = cachedDate,
                        libraryVersion = libraryVersion
                )

                database.cachedProjectsDao().insertFeed(cachedFeed)
                Log.d(TAG, "Saved the feed to cache")

            } catch (e: Exception) {
                Log.e(TAG, "Failed to cache the feed")
            } finally {
                outputStream.close()
                objectStream?.close()
            }
        }
    }

    /**
     * Returns a cached [Channel] if present and not expired.
     *
     * When there is an update of the RSS library, the cache is invalidated just to avoid errors if a
     * new parameter is added to the [Channel]
     *
     * If the cached value is expired, the cache is flushed.
     *
     * N.B. The [coroutineDispatcher] has to be Dispatchers.IO
     *
     * @param url The url of the RSS feed. The hash code of the url is the key.
     *
     */
    suspend fun getCachedFeed(url: String): Channel? = withContext(coroutineDispatcher) {
        val urlHash = url.hashCode()

        database.cachedProjectsDao().getCachedProject(urlHash)?.let { cachedFeed ->
            // Just to avoid problems when the Channel object changes
            if (cachedFeed.libraryVersion != BuildConfig.VERSION_CODE) {
                flushCachedFeed(url)
                return@withContext null
            }

            if (System.currentTimeMillis() - cachedFeed.cachedDate <= cacheDurationMillis) {
                val inputStream = ByteArrayInputStream(cachedFeed.byteArray)
                var objectInput: ObjectInputStream?
                try {
                    objectInput = ObjectInputStream(inputStream)
                    val channelFromCache = objectInput.readObject() as Channel
                    Log.d(TAG, "Feed restored from cache")
                    return@withContext channelFromCache
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load feed from cache")
                    return@withContext null
                }
            } else {
                flushCachedFeed(url)
                return@withContext null
            }
        }
    }

    companion object {
        const val TAG = "RSSParser CacheManager"

        private var INSTANCE: CacheManager? = null
        private val lock = Any()

        fun getInstance(context: Context, cacheDurationMillis: Long): CacheManager {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = CacheManager(CacheDatabase.getInstance(context), cacheDurationMillis)
                    }
                    return INSTANCE as CacheManager
                }
            }
            return INSTANCE as CacheManager
        }
    }
}