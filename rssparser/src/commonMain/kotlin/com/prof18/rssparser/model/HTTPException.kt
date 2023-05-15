package com.prof18.rssparser.model

data class HTTPException(
    val code: Int,
    override val message: String?
) : Exception()
