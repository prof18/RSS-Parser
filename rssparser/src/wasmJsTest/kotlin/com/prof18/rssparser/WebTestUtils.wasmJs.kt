package com.prof18.rssparser

import kotlinx.coroutines.asDeferred
import kotlin.js.Promise

@JsFun("(path) => window.fetch(path).then((r) => r.text())")
private external fun loadResource(path: String): Promise<JsAny>

@JsFun("(text) => text")
private external fun getString(text: JsAny): String

actual suspend fun loadBytesFromPath(path: String): String {
    val resourceText = loadResource(path).asDeferred<JsAny>().await()
    return getString(resourceText)
}
