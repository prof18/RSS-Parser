package com.prof.rssparser.caching

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class CachedFeedDao {
    @Query(CacheConstants.QUERY_GET_CACHED_PROJECT)
    abstract suspend fun getCachedProject(urlHash: Int): CachedFeed?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFeed(cachedFeed: CachedFeed)
}