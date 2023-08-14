package com.prof18.rssparser.internal

import platform.Foundation.NSError

internal data class NSXMLParsingException(
    val nsError: NSError,
    val failureReason: String?,
    val recoverySuggestion: String?,
    override val message: String?,
) : Throwable()
