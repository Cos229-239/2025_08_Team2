package com.example.ravengamingnews.data

/**
 * Data class representing a news article.
 */
data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val content: String,
    val author: String,
    val date: String,
    val game: String,
    val topic: String,
)
