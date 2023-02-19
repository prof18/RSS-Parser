package com.prof.rssparser.core

import com.prof.rssparser.HTTPException
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

        if (response.isSuccessful) {
            val body = requireNotNull(response.body)
            return body.byteStream()
        } else {
            throw HTTPException(
                code = response.code,
                message = response.message,
            )
        }
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
                if (response.isSuccessful) {
                    val body = requireNotNull(response.body)
                    continuation.resume(body.byteStream())
                } else {
                    val exception = HTTPException(
                        code = response.code,
                        message = response.message

                    )
                    continuation.resumeWithException(exception)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
            }
        })
    }

}
