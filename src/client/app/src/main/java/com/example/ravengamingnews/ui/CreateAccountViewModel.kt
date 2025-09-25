package com.example.ravengamingnews.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.R
import com.example.ravengamingnews.domain.model.UserSignUp
import com.example.ravengamingnews.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

private const val LOG_TAG = "CreateAccountViewModel"

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _dateOfBirth = MutableStateFlow("")
    val dateOfBirth: StateFlow<String> = _dateOfBirth

    private val _signUpErrorMessage = MutableStateFlow("")
    val signUpErrorMessage: StateFlow<String> = _signUpErrorMessage

    private val _isSignUpSuccess = MutableStateFlow(false)
    val isSignUpSuccess: StateFlow<Boolean> = _isSignUpSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Input validation state
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError

    private val _firstNameError = MutableStateFlow<String?>(null)
    val firstNameError: StateFlow<String?> = _firstNameError

    private val _lastNameError = MutableStateFlow<String?>(null)
    val lastNameError: StateFlow<String?> = _lastNameError

    private val _dateOfBirthError = MutableStateFlow<String?>(null)
    val dateOfBirthError: StateFlow<String?> = _dateOfBirthError

    fun onEmailChange(value: String) {
        _email.value = value
        validateEmail()
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        validatePassword()
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
        validateConfirmPassword()
    }

    fun onFirstNameChange(value: String) {
        _firstName.value = value
        validateFirstName()
    }

    fun onLastNameChange(value: String) {
        _lastName.value = value
        validateLastName()
    }

    fun onDateOfBirthChange(value: String) {
        _dateOfBirth.value = value
        validateDateOfBirth()
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
        if (_confirmPassword.value.isNotEmpty()) {
            validateConfirmPassword()
        }
    }

    private fun validateConfirmPassword() {
        val confirmPassword = _confirmPassword.value
        _confirmPasswordError.value = when {
            confirmPassword.isEmpty() -> context.getString(R.string.please_confirm_your_password)
            confirmPassword != _password.value -> context.getString(R.string.passwords_do_not_match)
            else -> null
        }
    }

    private fun validateFirstName() {
        val firstName = _firstName.value.trim()
        _firstNameError.value = when {
            firstName.isEmpty() -> context.getString(R.string.first_name_is_required)
            else -> null
        }
    }

    private fun validateLastName() {
        val lastName = _lastName.value.trim()
        _lastNameError.value = when {
            lastName.isEmpty() -> context.getString(R.string.last_name_is_required)
            else -> null
        }
    }

    private fun validateDateOfBirth() {
        val dateOfBirth = _dateOfBirth.value.trim()
        _dateOfBirthError.value = when {
            dateOfBirth.isEmpty() -> context.getString(R.string.date_of_birth_is_required)
            !isValidDateFormat(dateOfBirth) -> context.getString(R.string.invalid_date_format)
            else -> null
        }
    }

    private fun isValidDateFormat(date: String): Boolean {
        return try {
            LocalDate.parse(date)
            true
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Date parsing error: ${e.message}")
            false
        }
    }

    private fun validateAllFields(): Boolean {
        validateEmail()
        validatePassword()
        validateConfirmPassword()
        validateFirstName()
        validateLastName()
        validateDateOfBirth()

        return _emailError.value == null &&
                _passwordError.value == null &&
                _confirmPasswordError.value == null &&
                _firstNameError.value == null &&
                _lastNameError.value == null &&
                _dateOfBirthError.value == null
    }

    fun onCreateAccount() {
        if (!validateAllFields()) {
            _signUpErrorMessage.value = context.getString(R.string.fix_sign_up_errors)
            return
        }

        try {
            val dateOfBirth = LocalDate.parse(_dateOfBirth.value)

            _isLoading.value = true
            viewModelScope.launch {
                val userData = UserSignUp(
                    email = _email.value.trim(),
                    password = _password.value,
                    firstName = _firstName.value.trim(),
                    lastName = _lastName.value.trim(),
                    dateOfBirth = dateOfBirth
                )

                val result = signUpUseCase.execute(SignUpUseCase.Input(userData))

                _isLoading.value = false
                when (result) {
                    is SignUpUseCase.Output.Success -> {
                        _isSignUpSuccess.value = true
                    }
                    is SignUpUseCase.Output.Failure -> {
                        _signUpErrorMessage.value = context.getString(R.string.account_created_failure)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error creating account: ${e.message}")
            _isLoading.value = false
            _signUpErrorMessage.value = context.getString(R.string.account_created_failure)
        }
    }
}
