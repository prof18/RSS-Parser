
package com.prof.rssparser.sample

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.prof.rssparser.sample.common.getPlatformName


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        var text by remember { mutableStateOf("Hello, World!") }
        val platformName = getPlatformName()

        Button(onClick = {
            text = "Hello, ${platformName}"
        }) {
            Text(text)
        }
    }
}
