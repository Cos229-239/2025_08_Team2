package com.example.ravengamingnews.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a news article.
 */
@Serializable
data class ArticleWithGameDto(
    @SerialName("id")
    val id: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("content")
    val content: String,
    @SerialName("author")
    val author: String,
    @SerialName("date")
    val date: String,
    @SerialName("game")
    val game: GameDto,
    @SerialName("topic")
    val topic: String,
)
