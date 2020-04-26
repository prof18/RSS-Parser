package com.prof.rssparser.caching

object CacheConstants {
    const val CACHED_FEEDS_TABLE_NAME = "feeds"
    const val CACHED_FEEDS_URL_HASH = "url_hash"

    const val QUERY_GET_CACHED_PROJECT = """
        SELECT * FROM $CACHED_FEEDS_TABLE_NAME 
        WHERE $CACHED_FEEDS_URL_HASH = :urlHash
    """
}