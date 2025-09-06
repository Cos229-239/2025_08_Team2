package com.example.ravengamingnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.data.ArticleRepository
import com.example.ravengamingnews.data.ArticleWithGameDto
import com.example.ravengamingnews.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Instant

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _clickedArticles = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val clickedArticles: Flow<Map<Int, Boolean>> = _clickedArticles

    private val _articles = MutableStateFlow<List<Article>?>(listOf())
    val articleList: Flow<List<Article>?> = _articles

    init {
        getArticles()
    }

    fun getArticles() {
        viewModelScope.launch {
            val articles = articleRepository.getArticles()
            _articles.value = articles?.map { it.asDomainModel() }
        }
    }

    fun getArticleById(id: Int): Article? {
        return _articles.value?.find { it.id == id }
    }

    fun markArticleClicked(articleId: Int) {
        _clickedArticles.value = _clickedArticles.value + (articleId to true)
    }

    private fun ArticleWithGameDto.asDomainModel() = Article(
        id = this.id,
        title = this.title,
        summary = this.summary,
        content = this.content,
        author = this.author,
        date = Instant.Companion.parse(this.date),
        game = this.game.name,
        topic = this.topic
    )
}
