package com.prof.rssparser.sample.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val url = "https://www.androidauthority.com/feed"

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var articleListLive: MutableLiveData<MutableList<Article>>

    private val _snackbar = MutableLiveData<String>()

    val snackbar: LiveData<String>
        get() = _snackbar

    fun onSnackbarShowed() {
        _snackbar.value = null
    }

    fun getArticleList(): MutableLiveData<MutableList<Article>> {
        if (!::articleListLive.isInitialized) {
            articleListLive = MutableLiveData()
        }
        return articleListLive
    }

    private fun setArticleList(articleList: MutableList<Article>) {
        articleListLive.postValue(articleList)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun fetchFeed() {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                val parser = Parser()
                val articleList = parser.getArticles(url)
                setArticleList(articleList)
            } catch (e: Exception) {
                e.printStackTrace()
                _snackbar.value = "An error has occurred. Please retry"
                setArticleList(mutableListOf())
            }
        }
    }
}