package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.usecase.GetUserFiltersUseCase
import javax.inject.Inject

class GetUserFiltersUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GetUserFiltersUseCase {
    override suspend fun execute(input: Unit): GetUserFiltersUseCase.Output {
        return try {
            val filters = authRepository.getUserMetadata()?.filters
            if (filters != null) {
                GetUserFiltersUseCase.Output.Success(filters)
            } else {
                GetUserFiltersUseCase.Output.Failure(Throwable("No filters found"))
            }
        } catch (e: Exception) {
            GetUserFiltersUseCase.Output.Failure(e)
        }
    }
}
