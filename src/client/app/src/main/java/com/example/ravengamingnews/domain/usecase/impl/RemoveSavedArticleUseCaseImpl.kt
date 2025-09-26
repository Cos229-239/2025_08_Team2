package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.local.UserPreferencesRepository
import com.example.ravengamingnews.domain.usecase.RemoveSavedArticleUseCase
import javax.inject.Inject

class RemoveSavedArticleUseCaseImpl @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : RemoveSavedArticleUseCase {
    override suspend fun execute(input: Int): RemoveSavedArticleUseCase.Output {
        return try {
            val savedArticles = userPreferencesRepository.removeArticle(input)
            RemoveSavedArticleUseCase.Output.Success(savedArticles)
        } catch (e: Exception) {
            RemoveSavedArticleUseCase.Output.Failure
        }
    }
}
