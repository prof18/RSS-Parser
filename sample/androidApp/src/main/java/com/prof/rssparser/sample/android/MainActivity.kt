package com.prof.rssparser.sample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.prof.rssparser.sample.common.getPlatformName

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var text by remember { mutableStateOf("Hello, World!") }
                val platformName = getPlatformName()

                Button(onClick = {
                    text = "Hello, ${platformName}"
                }) {
                    Text(text)
                }
            }
        }
    }
}