package com.example.ravengamingnews.domain.usecase

import com.example.ravengamingnews.domain.model.UserFilters

interface GetUserFiltersUseCase : UseCase<Unit, GetUserFiltersUseCase.Output> {
    sealed class Output {
        data class Success(val filters: UserFilters) : Output()
        data class Failure(val error: Throwable) : Output()
    }
}
