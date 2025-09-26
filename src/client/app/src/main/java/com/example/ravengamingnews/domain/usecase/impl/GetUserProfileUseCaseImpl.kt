package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.usecase.GetUserProfileUseCase
import javax.inject.Inject

class GetUserProfileUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GetUserProfileUseCase {
    override suspend fun execute(input: Unit): GetUserProfileUseCase.Output {
        return try {
            val user = authRepository.refreshUserProfile()
            val metadata = authRepository.getUserMetadata()
            if (user != null && metadata != null) {
                GetUserProfileUseCase.Output.Success(
                    profile = com.example.ravengamingnews.domain.model.UserProfile(
                        email = user.email ?: "",
                        firstName = metadata.firstName,
                        lastName = metadata.lastName,
                        dateOfBirth = metadata.dateOfBirth
                    )
                )
            } else {
                GetUserProfileUseCase.Output.Failure
            }
        } catch (e: Exception) {
            GetUserProfileUseCase.Output.Failure
        }
    }
}
