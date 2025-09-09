package com.example.ravengamingnews.domain.model

sealed interface AuthState {
    data object Initializing : AuthState
    data object Authenticated : AuthState
    data object Unauthenticated : AuthState
}
