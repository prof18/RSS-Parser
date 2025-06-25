package com.prof18.rssparser.internal

import java.io.InputStream

internal fun InputStream.closeQuietly() {
    try {
        close()
    } catch (rethrown: RuntimeException) {
        throw rethrown
    } catch (_: Exception) {
    }
}
