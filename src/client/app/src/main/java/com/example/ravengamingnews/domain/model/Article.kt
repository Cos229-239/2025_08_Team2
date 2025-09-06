package com.example.ravengamingnews.domain.model

import kotlin.time.Instant

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val content: String,
    val author: String,
    val date: Instant,
    val game: String,
    val topic:String,
)
