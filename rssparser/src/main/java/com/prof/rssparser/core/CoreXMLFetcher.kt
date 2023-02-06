package com.prof.rssparser.core

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal object CoreXMLFetcher {
    @Throws(Exception::class)
    fun fetchXML(url: String, callFactory: Call.Factory): InputStream {
        val request = createRequest(url)
        val response = callFactory.newCall(request).execute()

        val body =  response.body
        requireNotNull(body)
        return body.byteStream()
    }

    suspend fun fetchXMLSuspendable(url: String, callFactory: Call.Factory): InputStream {
        val request = createRequest(url)
        return callFactory.newCall(request).await()
    }

    private fun createRequest(url: String): Request =
        Request.Builder()
            .url(url)
            .build()

    private suspend fun Call.await(): InputStream = suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }

        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = requireNotNull(response.body)
                continuation.resume(body.byteStream())
            }

            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
            }
        })
    }

}
