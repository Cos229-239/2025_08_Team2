package com.example.ravengamingnews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.data.Filter
import com.example.ravengamingnews.data.GameFilter
import com.example.ravengamingnews.data.TopicFilter
import com.example.ravengamingnews.domain.model.Article
import com.example.ravengamingnews.domain.usecase.GetArticlesUseCase
import com.example.ravengamingnews.domain.usecase.GetSavedArticlesUseCase
import com.example.ravengamingnews.domain.usecase.RemoveSavedArticleUseCase
import com.example.ravengamingnews.domain.usecase.SaveArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOG_TAG = "ArticleListViewModel"

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val removeSavedArticleUseCase: RemoveSavedArticleUseCase
) : ViewModel() {

    // Add this new state to track if initial loading has completed
    private val _initialLoadComplete = MutableStateFlow(false)
    val initialLoadComplete: StateFlow<Boolean> = _initialLoadComplete

    private val _savedArticles: MutableStateFlow<Set<Int>> = MutableStateFlow(emptySet())
    val savedArticles: Flow<Set<Int>> = _savedArticles
    private val _clickedArticles = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val clickedArticles: Flow<Map<Int, Boolean>> = _clickedArticles

    private val _articles = MutableStateFlow<List<Article>>(listOf())
    val articleList: Flow<List<Article>> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _browseFilter = MutableStateFlow<Filter?>(null)
    val browseFilter: Flow<Filter?> = _browseFilter

    init {
        _isRefreshing.value = false
        viewModelScope.launch {
            loadSavedArticles()
            _initialLoadComplete.value = true
        }
    }

    fun getArticles() {
        _isLoading.value = true
        _isRefreshing.value = false
        getArticlesAll()
    }

    fun refreshArticles() {
        _isLoading.value = false
        _isRefreshing.value = true
        getArticlesAll()
    }

    private fun getArticlesAll() {
        viewModelScope.launch {
            when (val result = getArticlesUseCase.execute(input = Unit)) {
                is GetArticlesUseCase.Output.Success -> {
                    _articles.emit(result.articles)
                }

                is GetArticlesUseCase.Output.Failure -> {
                    Log.e(LOG_TAG, "Error fetching articles")
                }
            }
            kotlinx.coroutines.delay(800)
            _isLoading.value = false
            _isRefreshing.value = false
        }
    }

    fun clearFilters() {
        _browseFilter.value = null
    }

    fun getArticlesByFilters(gameFilter: List<GameFilter>, topicFilter: List<TopicFilter>) {
        _isLoading.value = true
        getWithFilters(gameFilter, topicFilter)
    }

    fun refreshArticlesByFilters(gameFilter: List<GameFilter>, topicFilter: List<TopicFilter>) {
        _isRefreshing.value = true
        getWithFilters(gameFilter, topicFilter)
    }

    private fun getWithFilters(gameFilter: List<GameFilter>, topicFilter: List<TopicFilter>) {
        val selectedGames = gameFilter.filter { it.isChecked }.map { it.gameId }
        val selectedTopics = topicFilter.filter { it.isChecked }.map { it.topicEnum }
        if (selectedGames.isEmpty() && selectedTopics.isEmpty()) {
            getArticles()
            return
        }
        viewModelScope.launch {
            when (val result = getArticlesUseCase.execute(input = Unit)) {
                is GetArticlesUseCase.Output.Success -> {
                    _articles.emit(result.articles.filter { article ->
                        (selectedGames.isEmpty() || selectedGames.contains(article.gameId)) &&
                                (selectedTopics.isEmpty() || selectedTopics.contains(article.topic))
                    })
                }

                is GetArticlesUseCase.Output.Failure -> {
                    Log.e(LOG_TAG, "Error fetching articles by filters")
                }
            }
            kotlinx.coroutines.delay(800)
            _isLoading.value = false
            _isRefreshing.value = false
        }
    }

    fun getArticlesByFilter(filter: Filter) {
        _isLoading.value = true
        getWithFilter(filter)
    }

    fun refreshArticlesByFilter(filter: Filter) {
        _isRefreshing.value = true
        getWithFilter(filter)
    }

    private fun getWithFilter(filter: Filter) {
        _browseFilter.value = filter
        viewModelScope.launch {
            when (val result = getArticlesUseCase.execute(input = Unit)) {
                is GetArticlesUseCase.Output.Success -> {
                    _articles.emit(result.articles.filter { article ->
                        when (filter) {
                            is GameFilter -> article.gameId == filter.gameId
                            is TopicFilter -> article.topic == filter.topicEnum
                            else -> false
                        }
                    })
                }

                is GetArticlesUseCase.Output.Failure -> {
                    Log.e(LOG_TAG, "Error fetching articles by filter")
                }
            }
            kotlinx.coroutines.delay(800)
            _isLoading.value = false
            _isRefreshing.value = false
        }
    }

    fun markArticleClicked(articleId: Int) {
        _clickedArticles.value = _clickedArticles.value + (articleId to true)
    }

    fun toggleSaveArticle(articleId: Int) {
        if (_savedArticles.value.contains(articleId)) {
            removeSavedArticle(articleId)
        } else {
            saveArticle(articleId)
        }
    }

    fun loadSavedArticles() {
        viewModelScope.launch {
            when (val result = getSavedArticlesUseCase.execute(input = Unit)) {
                is GetSavedArticlesUseCase.Output.Success -> {
                    _savedArticles.emit(result.savedArticleIds)
                }

                is GetSavedArticlesUseCase.Output.Failure -> {
                    Log.e(LOG_TAG, "Error fetching saved articles")
                }
            }
        }
    }

    private fun saveArticle(articleId: Int) {
        viewModelScope.launch {
            when (val result = saveArticleUseCase.execute(input = articleId)) {
                is SaveArticleUseCase.Output.Success -> {
                    _savedArticles.emit(result.savedArticles)
                }

                is SaveArticleUseCase.Output.Failure -> {
                    Log.e(LOG_TAG, "Error saving article")
                }
            }
        }
    }

    private fun removeSavedArticle(articleId: Int) {
        viewModelScope.launch {
            when (val result = removeSavedArticleUseCase.execute(input = articleId)) {
                is RemoveSavedArticleUseCase.Output.Success -> {
                    _savedArticles.emit(result.savedArticles)
                }

                is RemoveSavedArticleUseCase.Output.Failure -> {
                    Log.e(LOG_TAG, "Error removing saved article")
                }
            }
        }
    }
}
