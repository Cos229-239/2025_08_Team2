package com.example.ravengamingnews.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UserMetadata(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val filters: UserFilters = UserFilters()
) {
    companion object {
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
        const val DATE_OF_BIRTH = "dateOfBirth"
        const val FILTERS = "filters"
    }
}
@Serializable
data class UserFilters(
    val games: List<Int> = emptyList(),
    val topics: List<Int> = emptyList()
)
