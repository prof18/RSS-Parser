package com.prof18.rssparser.sample

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        MainScreen()
    }
}

@Composable
private fun MainScreen() {
    MaterialTheme {
        val viewModel = remember { MainViewModel() }

        LaunchedEffect(Unit) {
            viewModel.getFeeds()
        }

        val uiState by viewModel.uiState
        val title = uiState.feed?.title ?: ""
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.background,
                    title = {
                        Text(
                            title,
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onBackground
                        )
                    },
                )
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
                            scope.launch {
                                viewModel.getFeeds()
                            }
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
