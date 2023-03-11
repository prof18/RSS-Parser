package com.prof.rssparser

import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile

actual fun readBinaryResource(
    resourceName: String
): ParserInput {
    val pathParts = resourceName.split("[.|/]".toRegex())
    val path = NSBundle.mainBundle
        .pathForResource("resources/${pathParts[0]}", pathParts[1])

    val data = NSData.dataWithContentsOfFile(path!!)

    return ParserInput(data!!)
}
