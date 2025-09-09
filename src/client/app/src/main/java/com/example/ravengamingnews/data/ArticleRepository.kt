package com.example.ravengamingnews.data

interface ArticleRepository {
    suspend fun getArticles(): List<ArticleWithGameDto>?
}
