package com.prof.rssparser.caching

internal object CacheConstants {
    const val CACHED_FEEDS_TABLE_NAME = "feeds"
    const val CACHED_FEEDS_URL_HASH = "url_hash"
    const val CACHED_FEEDS_CHARSET = "charset"

    const val QUERY_GET_CACHED_FEED = """
        SELECT * FROM $CACHED_FEEDS_TABLE_NAME 
        WHERE $CACHED_FEEDS_URL_HASH = :urlHash
        AND $CACHED_FEEDS_CHARSET = :charset
    """

    const val DELETE_ALL_QUERY = """
        DELETE FROM $CACHED_FEEDS_TABLE_NAME 
    """

    const val DELETE_CACHED_FEED = """
        DELETE FROM $CACHED_FEEDS_TABLE_NAME 
        WHERE $CACHED_FEEDS_URL_HASH = :urlHash
    """
}
