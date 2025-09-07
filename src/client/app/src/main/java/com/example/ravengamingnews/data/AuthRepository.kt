package com.example.ravengamingnews.data

import com.example.ravengamingnews.domain.model.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<AuthState>
    val continuedAsGuest: StateFlow<Boolean>

    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun continueAsGuest(): Boolean
    suspend fun signOut()
}
