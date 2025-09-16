package com.example.ravengamingnews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.domain.model.Article
import com.example.ravengamingnews.domain.usecase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOG_TAG = "ArticleListViewModel"

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {
    private val _clickedArticles = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val clickedArticles: Flow<Map<Int, Boolean>> = _clickedArticles

    private val _articles = MutableStateFlow<List<Article>>(listOf())
    val articleList: Flow<List<Article>> = _articles

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getArticles()
    }

    fun getArticles() {
        viewModelScope.launch {
            _isRefreshing.value = true
            when (val result = getArticlesUseCase.execute(input = Unit)) {
                is GetArticlesUseCase.Output.Success -> {
                    _articles.emit(result.articles)
                    _isRefreshing.value = false
                }

                is GetArticlesUseCase.Output.Failure -> {
                    Log.e(LOG_TAG, "Error fetching articles")
                    _isRefreshing.value = false
                }
            }
        }
    }

    fun getArticleById(id: Int): Article? {
        return _articles.value.find { it.id == id }
    }

    fun markArticleClicked(articleId: Int) {
        _clickedArticles.value = _clickedArticles.value + (articleId to true)
    }
}
