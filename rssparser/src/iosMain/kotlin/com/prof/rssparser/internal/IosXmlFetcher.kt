package com.prof.rssparser.internal

import com.prof.rssparser.model.HTTPException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSHTTPURLResponse
import platform.Foundation.NSURL
import platform.Foundation.NSURLResponse
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithURL

internal class IosXmlFetcher(
    private val nsUrlSession: NSURLSession,
): XmlFetcher {

    override suspend fun fetchXml(url: String): ParserInput = suspendCancellableCoroutine { continuation ->
       val task = nsUrlSession.dataTaskWithURL(NSURL(string = url)) { data: NSData?, response: NSURLResponse?, error: NSError? ->
           if (error != null) {
               val throwable = Throwable(
                   message = error.description
               )
               continuation.resumeWithException(throwable)
           } else if (response != null && (response as NSHTTPURLResponse).statusCode !in 200..299 ) {
                val exception = HTTPException(
                    code = response.statusCode.toInt(),
                    message = response.description,
                )
                continuation.resumeWithException(exception)
            } else if (data != null) {
                continuation.resume(ParserInput(data))
            }
       }

        continuation.invokeOnCancellation {
            task.cancel()
        }

        task.resume()
    }
}
