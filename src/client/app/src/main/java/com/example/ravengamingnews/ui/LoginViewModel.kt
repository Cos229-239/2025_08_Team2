package com.example.ravengamingnews.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.R
import com.example.ravengamingnews.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    @param:ApplicationContext private val context: Context
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password: Flow<String> = _password

    private val _authMessage = MutableStateFlow("")
    val authMessage = _authMessage

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    private fun validateEmail() {
        val email = _email.value.trim()
        _emailError.value = when {
            email.isEmpty() -> context.getString(R.string.email_is_required)
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> context.getString(R.string.invalid_email_format)
            else -> null
        }
    }

    private fun validatePassword() {
        val password = _password.value
        _passwordError.value = when {
            password.isEmpty() -> context.getString(R.string.password_is_required)
            password.length < 6 -> context.getString(R.string.password_must_be_at_least)
            else -> null
        }
    }
    private fun validateAllFields(): Boolean {
        validateEmail()
        validatePassword()

        return _emailError.value == null &&
                _passwordError.value == null
    }
    fun onSignIn() {
        if (!validateAllFields()) {
            _authMessage.value = context.getString(R.string.login_failed)
            return
        }

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
                    authMessage.emit(context.getString(R.string.login_failed))
                }
            }
        }
    }
}
