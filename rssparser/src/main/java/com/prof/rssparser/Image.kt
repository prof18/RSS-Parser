package com.prof.rssparser

import java.io.Serializable

data class Image(
        var title: String? = null,
        var url: String? = null,
        var link: String? = null,
        var description: String? = null
): Serializable