package com.prof18.rssparser.internal

import com.prof18.rssparser.exception.HttpException
import okhttp3.Call
import okhttp3.Request
import okhttp3.coroutines.executeAsync

internal class JvmXmlFetcher(
    private val callFactory: Call.Factory,
) : XmlFetcher {

    override suspend fun fetchXml(url: String): ParserInput {
        val request = createRequest(url)
        val baseUrl = request.url.scheme + "://" + request.url.host
        val response = callFactory.newCall(request).executeAsync()
        if (!response.isSuccessful) {
            val exception = HttpException(
                code = response.code,
                message = response.message,
            )
            response.close()
            throw exception
        }
        return ParserInput(
            inputStream = response.body.byteStream(),
            baseUrl = baseUrl,
        )
    }

    override suspend fun fetchXmlAsString(url: String): String {
        val request = createRequest(url)
        val response = callFactory.newCall(request).executeAsync()
        if (!response.isSuccessful) {
            val exception = HttpException(
                code = response.code,
                message = response.message,
            )
            response.close()
            throw exception
        }
        return response.body.string()
    }

    private fun createRequest(url: String): Request =
        Request.Builder()
            .url(url)
            .build()
}
