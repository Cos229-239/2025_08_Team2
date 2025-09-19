package com.example.ravengamingnews.domain.usecase

import com.example.ravengamingnews.domain.model.UserProfile

interface UpdateUserProfileUseCase : UseCase<UpdateUserProfileUseCase.Input, UpdateUserProfileUseCase.Output> {
    class Input(val userProfile: UserProfile)
    sealed class Output {
        data class Success(val requiresConfirmation: Boolean): Output()
        data class Failure(val error: Throwable): Output()
    }
}
