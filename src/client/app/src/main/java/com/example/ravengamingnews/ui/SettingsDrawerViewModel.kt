package com.example.ravengamingnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsDrawerViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isGuest = authRepository.continuedAsGuest

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}
