package com.example.ravengamingnews.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val isEditing = MutableStateFlow(false)
    val editing: StateFlow<Boolean> = isEditing

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _dateOfBirth = MutableStateFlow("")
    val dateOfBirth: StateFlow<String> = _dateOfBirth

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

    fun setEditing(edit: Boolean) {
        isEditing.value = edit
    }
}
