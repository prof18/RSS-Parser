package com.prof.rssparser

import platform.Foundation.NSBundle
import kotlin.test.Test
import kotlin.test.assertTrue

class test {

    @Test
    fun test() {
        val path = NSBundle.mainBundle.pathForResource("resources/feed-bing-image", "FILE_EXTENSION") ?: ""

        println(path)
        assertTrue(true)
    }

}