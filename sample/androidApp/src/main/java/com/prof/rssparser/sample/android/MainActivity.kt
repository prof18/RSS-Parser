package com.prof.rssparser.sample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        setContent {
            MaterialTheme {
                val uiState by viewModel.uiState

                val title = uiState.feed?.title ?: ""

                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = MaterialTheme.colors.background
                        ) {
                            Text(
                                title,
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    },
                ) { paddingValues ->
                    when {
                        uiState.isLoading -> {
                            LoadingProgress()
                        }

                        uiState.error != null -> {
                            val errorMessage = uiState.error ?: ""
                            ErrorView(
                                errorMessage = errorMessage,
                                onRetryClick = {
                                    viewModel.getFeeds()
                                },
                            )
                        }

                        uiState.feed != null -> {
                            val items = uiState.feed?.items ?: emptyList()
                            FeedList(
                                padding = paddingValues,
                                feedItems = items,
                            )
                        }
                    }
                }
            }
        }
    }
}
