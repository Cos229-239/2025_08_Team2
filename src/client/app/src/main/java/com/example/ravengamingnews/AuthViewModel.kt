package com.example.ravengamingnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoggedIn: Boolean = false,
    val continuedAsGuest: Boolean = false,
    val isLoading: Boolean = false,
)

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            // Check SharedPreferences, token validation, etc.
            val isLoggedIn = false // Replace with actual check
            _authState.value = AuthState(
                isLoggedIn = isLoggedIn,
                isLoading = false
            )
        }
    }

    fun login() {
        _authState.value = _authState.value.copy(isLoggedIn = true)
    }

    fun continueAsGuest() {
        _authState.value = _authState.value.copy(continuedAsGuest = true)
    }

    fun logout() {
        _authState.value = AuthState(isLoggedIn = false, continuedAsGuest = false, isLoading = false)
    }
}
