package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfFile
import platform.posix.getenv

@OptIn(ExperimentalForeignApi::class)
internal actual suspend fun readFileFromResources(
    resourceName: String,
): ParserInput {
    val s = getenv("TEST_RESOURCES_ROOT")?.toKString()
    val path = "$s/$resourceName"
    val data = NSData.dataWithContentsOfFile(path)
    return ParserInput(requireNotNull(value = data), baseUrl = BASE_FEED_URL)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal actual suspend fun readFileFromResourcesAsString(
    resourceName: String,
): String {
    val s = getenv("TEST_RESOURCES_ROOT")?.toKString()
    val path = "$s/$resourceName"
    val data = requireNotNull(NSData.dataWithContentsOfFile(path))
    return NSString.create(data, NSUTF8StringEncoding).toString()
}

internal actual val currentTarget = CurrentTarget.APPLE
