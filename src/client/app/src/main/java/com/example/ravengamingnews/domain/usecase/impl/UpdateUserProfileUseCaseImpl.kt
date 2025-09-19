package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.usecase.UpdateUserProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserProfileUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : UpdateUserProfileUseCase {
    override suspend fun execute(input: UpdateUserProfileUseCase.Input): UpdateUserProfileUseCase.Output {
        return withContext(Dispatchers.IO) {
            val updated = authRepository.updateUserProfile(
                email = input.userProfile.email,
                firstName = input.userProfile.firstName,
                lastName = input.userProfile.lastName,
            )
            if (!updated) {
                UpdateUserProfileUseCase.Output.Failure(Exception("Failed to update user profile"))
            } else {
                UpdateUserProfileUseCase.Output.Success
            }
        }
    }
}
