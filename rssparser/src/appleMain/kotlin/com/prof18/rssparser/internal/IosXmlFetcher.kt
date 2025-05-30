package com.prof18.rssparser.internal

import com.prof18.rssparser.exception.HttpException
import kotlinx.cinterop.BetaInteropApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSHTTPURLResponse
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSURLResponse
import platform.Foundation.NSURLSession
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataTaskWithURL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class IosXmlFetcher(
    private val nsUrlSession: NSURLSession,
) : XmlFetcher {
    override suspend fun fetchXml(url: String): ParserInput = suspendCancellableCoroutine { continuation ->
        val nsUrl = NSURL(string = url)
        val baseUrl = nsUrl.scheme + "://" + nsUrl.host
        val task = nsUrlSession.dataTaskWithURL(
            url = nsUrl
        ) { data: NSData?, response: NSURLResponse?, error: NSError? ->
            if (error != null) {
                val throwable = Throwable(
                    message = error.description
                )
                continuation.resumeWithException(throwable)
            } else if (response != null && (response as NSHTTPURLResponse).statusCode !in 200..299) {
                val exception = HttpException(
                    code = response.statusCode.toInt(),
                    message = response.description,
                )
                continuation.resumeWithException(exception)
            } else if (data != null) {
                continuation.resume(ParserInput(data, baseUrl))
            }
        }

        continuation.invokeOnCancellation {
            task.cancel()
        }

        task.resume()
    }

    @OptIn(BetaInteropApi::class)
    override suspend fun fetchXmlAsString(url: String): String {
        val input = fetchXml(url)
        return NSString.create(input.data, NSUTF8StringEncoding).toString()
    }
}
