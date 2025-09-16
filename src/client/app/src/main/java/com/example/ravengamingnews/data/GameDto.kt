package com.example.ravengamingnews.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    @SerialName("id")
    val id: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("name")
    val name: String,
)
