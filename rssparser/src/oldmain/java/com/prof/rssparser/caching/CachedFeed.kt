package com.prof.rssparser.caching

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CacheConstants.CACHED_FEEDS_TABLE_NAME)
internal class CachedFeed(
        @PrimaryKey
        @ColumnInfo(name = "url_hash")
        var urlHash: Int,

        @ColumnInfo(name = "byte_data", typeAffinity = ColumnInfo.BLOB)
        var byteArray: ByteArray,

        @ColumnInfo(name = "cached_date")
        var cachedDate: Long,

        @ColumnInfo(name = "library_version")
        var libraryVersion: Int,

        @ColumnInfo(name = "charset")
        var charset: String
)
