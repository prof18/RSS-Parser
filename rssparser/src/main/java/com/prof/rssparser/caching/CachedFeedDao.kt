package com.prof.rssparser.caching

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface CachedFeedDao {
    @Query(CacheConstants.QUERY_GET_CACHED_FEED)
    suspend fun getCachedFeed(urlHash: Int): CachedFeed?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeed(cachedFeed: CachedFeed)

    @Query(CacheConstants.DELETE_ALL_QUERY)
    suspend fun deleteAllFeed()

    @Query(CacheConstants.DELETE_CACHED_FEED)
    suspend fun deleteFeed(urlHash: Int)
}
