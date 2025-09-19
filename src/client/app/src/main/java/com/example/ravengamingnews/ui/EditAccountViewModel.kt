package com.example.ravengamingnews.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.R
import com.example.ravengamingnews.domain.model.UserProfile
import com.example.ravengamingnews.domain.usecase.GetUserProfileUseCase
import com.example.ravengamingnews.domain.usecase.UpdateUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

private const val LOG_TAG = "EditAccountViewModel"

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _isLoading

    private val isEditing = MutableStateFlow(false)
    val editing: StateFlow<Boolean> = isEditing

    private val _infoMessage = MutableStateFlow<String?>(null)
    val infoMessage: StateFlow<String?> = _infoMessage

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _firstNameError = MutableStateFlow<String?>(null)
    val firstNameError: StateFlow<String?> = _firstNameError

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _lastNameError = MutableStateFlow<String?>(null)
    val lastNameError: StateFlow<String?> = _lastNameError

    private val _dateOfBirth = MutableStateFlow("")
    val dateOfBirth: StateFlow<String> = _dateOfBirth

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        validateEmail()
    }

    private fun validateEmail() {
        val email = _email.value.trim()
        _emailError.value = when {
            email.isEmpty() -> context.getString(R.string.email_is_required)
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> context.getString(R.string.invalid_email_format)

            else -> null
        }
    }

    fun onFirstNameChange(newFirstName: String) {
        _firstName.value = newFirstName
        validateFirstName()
    }

    private fun validateFirstName() {
        val firstName = _firstName.value.trim()
        _firstNameError.value = when {
            firstName.isEmpty() -> context.getString(R.string.first_name_is_required)
            else -> null
        }
    }

    fun onLastNameChange(newLastName: String) {
        _lastName.value = newLastName
        validateLastName()
    }

    fun clearInfoMessage() {
        _infoMessage.value = null
    }

    private fun validateLastName() {
        val lastName = _lastName.value.trim()
        _lastNameError.value = when {
            lastName.isEmpty() -> context.getString(R.string.last_name_is_required)
            else -> null
        }
    }

    private fun validateAllFields(): Boolean {
        validateEmail()
        validateFirstName()
        validateLastName()

        return _emailError.value == null &&
                _firstNameError.value == null &&
                _lastNameError.value == null
    }

    fun getUserProfile() {
        viewModelScope.launch {
            when (val result = getUserProfileUseCase.execute(input = Unit)) {
                is GetUserProfileUseCase.Output.Success -> {
                    _email.value = result.profile.email
                    _firstName.value = result.profile.firstName
                    _lastName.value = result.profile.lastName
                    _dateOfBirth.value = result.profile.dateOfBirth.toString()
                }

                is GetUserProfileUseCase.Output.Failure -> {
                    // Handle failure if needed
                }
            }
        }
    }

    fun onSaveChanges() {
        if (!validateAllFields()) {
            // fields will show errors - do not proceed
            return
        }

        try {
            _isLoading.value = true
            viewModelScope.launch {
                val userData = UserProfile(
                    email = _email.value.trim(),
                    firstName = _firstName.value.trim(),
                    lastName = _lastName.value.trim(),
                    dateOfBirth = LocalDate.parse(_dateOfBirth.value) // Read-only field
                )

                val result = updateUserProfileUseCase.execute(
                    input = UpdateUserProfileUseCase.Input(userProfile = userData)
                )

                _isLoading.value = false
                when (result) {
                    is UpdateUserProfileUseCase.Output.Success -> {
                        if (result.requiresConfirmation) {
                            _infoMessage.value = context.getString(R.string.confirmation_email_sent)
                        } else {
                            _infoMessage.value =
                                context.getString(R.string.account_updated_successfully)
                        }
                        isEditing.value = false
                    }

                    is UpdateUserProfileUseCase.Output.Failure -> {
                        _infoMessage.value = context.getString(R.string.error_updating_account)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error updating account: ${e.message}")
            _isLoading.value = false
        }
    }

    fun setEditing(edit: Boolean) {
        isEditing.value = edit
    }
}
