package com.prof18.rssparser

import platform.Foundation.NSError

data class ParsingException(
    val nsError: NSError,
    val failureReason: String?,
    val recoverySuggestion: String?,
    override val message: String?,
) : Throwable()
