package com.prof.rssparser.sample.android

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.sample.common.DI
import com.prof.rssparser.sample.common.UIState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val url = "https://www.androidauthority.com/feed"

    private val feedRepository = DI.feedRepository

    private val _uiState: MutableState<UIState> = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState

    init {
        getFeeds()
    }

    fun getFeeds() {
        viewModelScope.launch {
            try {
                val feed = feedRepository.getFeed(url)
                _uiState.value = UIState(
                    isLoading = false,
                    feed = feed
                )
            } catch (e: Exception) {
                println("Something wrong while getting the feeds")
                e.printStackTrace()
                _uiState.value = UIState(
                    isLoading = false,
                    error = "Something wrong"
                )
            }
        }
    }
}
