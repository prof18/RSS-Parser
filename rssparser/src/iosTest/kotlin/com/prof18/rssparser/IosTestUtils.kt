package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile
import platform.posix.getenv

@OptIn(ExperimentalForeignApi::class)
internal actual fun readFileFromResources(
    resourceName: String
): ParserInput {
    val s = getenv("TEST_RESOURCES_ROOT")?.toKString()
    val path = "$s/${resourceName}"
    val data = NSData.dataWithContentsOfFile(path)
    return ParserInput(requireNotNull(data))
}

internal actual val currentTarget = CurrentTarget.IOS