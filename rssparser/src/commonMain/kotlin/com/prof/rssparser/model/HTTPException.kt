package com.prof.rssparser.model

data class HTTPException(
    val code: Int,
    override val message: String?
) : Exception()
