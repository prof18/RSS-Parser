package com.prof.rssparser.internal

internal fun <K, V> Map<K, V>.getValueOrNull(key: K): V? {
    if (!containsKey(key)) {
        return null
    }
    return get(key)
}
