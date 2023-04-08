package com.prof.rssparser

public fun <K, V> Map<K, V>.getValueOrNull(key: K): V? {
    if (!containsKey(key)) {
        return null
    }
    return get(key)
}
