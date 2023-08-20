package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile

internal actual fun readBinaryResource(
    resourceName: String
): ParserInput {
    val pathParts = resourceName.split("[.|/]".toRegex())
    val path = NSBundle.mainBundle
        .pathForResource("resources/${pathParts[0]}", pathParts[1])

    val data = NSData.dataWithContentsOfFile(path!!)

    return ParserInput(data!!)
}

internal actual val currentTarget = CurrentTarget.IOS
