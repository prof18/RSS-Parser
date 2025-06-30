package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput

internal actual val currentTarget = CurrentTarget.WEB

expect suspend fun loadBytesFromPath(path: String): String

internal actual suspend fun readFileFromResources(
    resourceName: String,
): ParserInput {
    val string = loadBytesFromPath("/test-resources/$resourceName")
    return ParserInput(string, baseUrl = BASE_FEED_URL)
}

internal actual suspend fun readFileFromResourcesAsString(
    resourceName: String,
): String {
    val parserInput = readFileFromResources(resourceName)
    return parserInput.data
}
