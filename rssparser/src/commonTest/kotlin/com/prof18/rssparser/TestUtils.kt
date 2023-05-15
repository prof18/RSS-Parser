package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect fun readBinaryResource(
    resourceName: String
): ParserInput

const val RESOURCES_PATH = "./src/commonTest/resources"