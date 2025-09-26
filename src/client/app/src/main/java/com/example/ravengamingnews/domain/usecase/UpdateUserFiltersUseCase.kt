package com.example.ravengamingnews.domain.usecase

import com.example.ravengamingnews.domain.model.UserFilters

interface UpdateUserFiltersUseCase :
    UseCase<UpdateUserFiltersUseCase.Input, UpdateUserFiltersUseCase.Output> {
    data class Input(val filters: UserFilters)

    sealed class Output {
        data object Success : Output()
        data class Failure(val error: Throwable) : Output()
    }
}
