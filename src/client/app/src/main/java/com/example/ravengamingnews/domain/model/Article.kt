package com.example.ravengamingnews.domain.model

import com.example.ravengamingnews.data.TopicEnum
import kotlin.time.Instant

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val content: String,
    val author: String,
    val date: Instant,
    val gameId: Int,
    val gameNameResId: Int?, // Resource ID for localized game name
    val topic: TopicEnum?,
    val topicNameResId: Int?, // Resource ID for localized topic name
)
