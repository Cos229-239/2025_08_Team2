package com.example.ravengamingnews.domain.usecase

import com.example.ravengamingnews.domain.model.UserProfile

interface GetUserProfileUseCase : UseCase<Unit, GetUserProfileUseCase.Output> {
    sealed class Output {
        data class Success(val profile: UserProfile) : Output()
        object Failure : Output()
    }
}
