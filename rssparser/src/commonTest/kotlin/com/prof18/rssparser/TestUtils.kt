package com.prof18.rssparser

import com.prof18.rssparser.internal.ParserInput

internal expect fun readFileFromResources(
    resourceName: String
): ParserInput

internal expect fun readFileFromResourcesAsString(
    resourceName: String
): String

internal expect val currentTarget: CurrentTarget
