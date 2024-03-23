package com.prof18.rssparser.sample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.prof18.rssparser.sample.common.DI
import kotlinx.coroutines.launch
import java.net.URL


fun main() = application {
    var isMainScreenShow by remember { mutableStateOf(true) }

    if (isMainScreenShow) {
        MainScreen(
            onShowCheckerClick = {
                isMainScreenShow = false
            }
        )
    } else {
        CheckerScreen()
    }
}

@Composable
private fun ApplicationScope.MainScreen(onShowCheckerClick: () -> Unit) {
    Window(onCloseRequest = ::exitApplication) {
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
                        actions = {
                            IconButton(
                                onClick = {
                                    onShowCheckerClick()
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
}

@Composable
private fun ApplicationScope.CheckerScreen() {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            var rssLink by remember { mutableStateOf("") }
            var parsingResult by remember { mutableStateOf("") }

            LaunchedEffect(rssLink) {
                if (rssLink.isNotEmpty() && isValidURL(rssLink)) {
                    val feedRepository = DI.feedRepository
                    parsingResult = try {
                        val result = feedRepository.getFeed(rssLink)
                        result.toString()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        e.toString()
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                item {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        label = { Text("RSS feed link") },
                        value = rssLink,
                        onValueChange = { rssLink = it },
                        singleLine = true,
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = rssLink.isNotBlank(),
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(
                                    onClick = {
                                        rssLink = ""
                                        parsingResult = ""
                                    }
                                ) {
                                    Icon(Icons.Outlined.Delete, "Clear")
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Uri,
                            autoCorrect = false,
                        ),
                    )
                }

                item {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = parsingResult
                    )
                }
            }
        }
    }
}

fun isValidURL(url: String?): Boolean {
    return try {
        URL(url).toURI()
        true
    } catch (e: Exception) {
        false
    }
}
