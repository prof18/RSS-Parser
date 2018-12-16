package com.prof.rssparser.sample.kotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope {

    private val url = "https://www.androidauthority.com/feed"

    private val viewModelJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    private lateinit var articleListLive: MutableLiveData<MutableList<Article>>

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
        launch(coroutineContext) {
            try {
                val parser = Parser()
                val articleList = parser.getArticles(url)
                setArticleList(articleList)
            } catch (e: Exception) {
                e.printStackTrace()
                setArticleList(mutableListOf())
            }
        }
    }


}