package com.prof18.rssparser.sample.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        setContent {
            MaterialTheme {
                val uiState by viewModel.uiState
                val title = uiState.feed?.title ?: ""
                val context = LocalContext.current

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
                            actions = {
                                IconButton(
                                    onClick = {
                                        context.startActivity(
                                            Intent(context, CheckerActivity::class.java)
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = null,
                                    )
                                }
                            }
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
