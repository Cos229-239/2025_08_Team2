package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.local.UserPreferencesRepository
import com.example.ravengamingnews.domain.usecase.GetSavedArticlesUseCase
import javax.inject.Inject

class GetSavedArticlesUseCaseImpl @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : GetSavedArticlesUseCase {
    override suspend fun execute(input: Unit): GetSavedArticlesUseCase.Output {
        return try {
            val savedArticles = userPreferencesRepository.savedArticles()
            GetSavedArticlesUseCase.Output.Success(savedArticles)
        } catch (e: Exception) {
            GetSavedArticlesUseCase.Output.Failure
        }
    }
}
