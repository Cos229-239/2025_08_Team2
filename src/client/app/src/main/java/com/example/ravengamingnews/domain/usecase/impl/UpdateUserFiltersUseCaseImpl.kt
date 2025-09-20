package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.usecase.UpdateUserFiltersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserFiltersUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : UpdateUserFiltersUseCase {
    override suspend fun execute(input: UpdateUserFiltersUseCase.Input): UpdateUserFiltersUseCase.Output {
        return withContext(Dispatchers.IO) {
            try {
                authRepository.updateUserFilters(input.filters)
                UpdateUserFiltersUseCase.Output.Success
            } catch (e: Exception) {
                UpdateUserFiltersUseCase.Output.Failure(e)
            }
        }
    }
}
