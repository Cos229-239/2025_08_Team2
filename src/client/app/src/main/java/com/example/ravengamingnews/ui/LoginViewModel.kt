package com.example.ravengamingnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {
    private val _email = MutableStateFlow("user@patchraven.com")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("user")
    val password: Flow<String> = _password

    private val _authMessage = MutableStateFlow("")
    val authMessage = _authMessage

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onSignIn() {
        viewModelScope.launch {
            val result = signInUseCase.execute(
                SignInUseCase.Input(
                    email = _email.value,
                    password = _password.value
                )
            )
            when (result) {
                is SignInUseCase.Output.Success -> {
                    authMessage.emit("")
                }

                else -> {
                    message.emit("Login failed.")
                }
            }
        }
    }
}
