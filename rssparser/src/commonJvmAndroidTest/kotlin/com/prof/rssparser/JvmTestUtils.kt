package com.prof.rssparser

import java.io.File
import java.io.FileInputStream

actual fun readBinaryResource(
    resourceName: String,
): ParserInput {
    val file = File("$RESOURCES_PATH/$resourceName")
    return ParserInput(
        inputStream = FileInputStream(file)
    )
}
