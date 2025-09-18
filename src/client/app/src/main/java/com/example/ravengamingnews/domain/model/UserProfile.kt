package com.example.ravengamingnews.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val email: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate
)
