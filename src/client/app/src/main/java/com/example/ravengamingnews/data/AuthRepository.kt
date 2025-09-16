package com.example.ravengamingnews.data

import com.example.ravengamingnews.domain.model.AuthState
import com.example.ravengamingnews.domain.model.UserFilters
import com.example.ravengamingnews.domain.model.UserMetadata
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface AuthRepository {
    val authState: StateFlow<AuthState>
    val continuedAsGuest: StateFlow<Boolean>

    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        dateOfBirth: LocalDate
    ): Boolean

    suspend fun updateUserProfile(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        dateOfBirth: LocalDate
    ): Boolean

    suspend fun getUserMetadata(): UserMetadata?
    suspend fun updateUserFilters(filters: UserFilters): Boolean

    suspend fun continueAsGuest(): Boolean
    suspend fun signOut()
}
