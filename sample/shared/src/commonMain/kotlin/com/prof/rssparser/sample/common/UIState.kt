package com.prof.rssparser.sample.common

data class UIState(
    val isLoading: Boolean = true,
    val feed: Feed? = null,
    val error: String? = null
)