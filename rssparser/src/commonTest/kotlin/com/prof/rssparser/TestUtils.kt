package com.prof.rssparser

import com.prof.rssparser.internal.ParserInput

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect fun readBinaryResource(
    resourceName: String
): ParserInput

const val RESOURCES_PATH = "./src/commonTest/resources"