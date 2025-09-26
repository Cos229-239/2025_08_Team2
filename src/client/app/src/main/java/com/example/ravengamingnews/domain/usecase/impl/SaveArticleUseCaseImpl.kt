package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.local.UserPreferencesRepository
import com.example.ravengamingnews.domain.usecase.SaveArticleUseCase
import javax.inject.Inject

class SaveArticleUseCaseImpl @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : SaveArticleUseCase {
    override suspend fun execute(input: Int): SaveArticleUseCase.Output {
        return try {
            val savedArticles = userPreferencesRepository.saveArticle(input)
            SaveArticleUseCase.Output.Success(savedArticles)
        } catch (e: Exception) {
            SaveArticleUseCase.Output.Failure
        }
    }
}
