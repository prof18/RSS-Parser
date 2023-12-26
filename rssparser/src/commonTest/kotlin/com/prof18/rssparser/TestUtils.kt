package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect fun readFileFromResources(
    resourceName: String
): ParserInput

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect val currentTarget: CurrentTarget
