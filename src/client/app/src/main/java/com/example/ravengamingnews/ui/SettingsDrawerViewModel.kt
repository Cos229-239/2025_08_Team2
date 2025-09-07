package com.example.ravengamingnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsDrawerViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun isSignedIn(): Boolean {
        return !authRepository.continuedAsGuest.value
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}
