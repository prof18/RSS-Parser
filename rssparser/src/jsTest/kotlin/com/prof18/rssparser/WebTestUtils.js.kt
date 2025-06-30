package com.prof18.rssparser

import kotlinx.browser.window
import kotlinx.coroutines.await

actual suspend fun loadBytesFromPath(path: String): String {
    return window.fetch(path)
        .await()
        .text()
        .await()
}
