package com.prof.rssparser

data class HTTPException(
    val code: Int,
    override val message: String?
) : Exception()
