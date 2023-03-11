package com.prof.rssparser

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect fun readBinaryResource(
    resourceName: String
): ParserInput

const val RESOURCES_PATH = "./src/commonTest/resources"