package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput
import java.io.File
import java.io.FileInputStream

internal actual fun readFileFromResources(
    resourceName: String,
): ParserInput {
    val path = System.getenv("TEST_RESOURCES_ROOT")
    val file = File("$path/$resourceName")
    return ParserInput(
        inputStream = FileInputStream(file)
    )
}

internal actual fun readFileFromResourcesAsString(
    resourceName: String,
): String {
    val path = System.getenv("TEST_RESOURCES_ROOT")
    val file = File("$path/$resourceName")
    return file.readText()
}
