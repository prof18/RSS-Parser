package com.prof18.rssparser.model

public data class RawMediaContent(
    val url: String?,
    val type: String?,
    val medium: String?,
    val width: Int? = null,
    val height: Int? = null,
)
