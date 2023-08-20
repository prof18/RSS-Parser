package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import java.io.File
import java.io.FileInputStream

internal actual fun readBinaryResource(
    resourceName: String,
): ParserInput {
    val file = File("$RESOURCES_PATH/$resourceName")
    return ParserInput(
        inputStream = FileInputStream(file)
    )
}
