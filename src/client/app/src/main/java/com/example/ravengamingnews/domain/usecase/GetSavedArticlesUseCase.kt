package com.example.ravengamingnews.domain.usecase

interface GetSavedArticlesUseCase : UseCase<Unit, GetSavedArticlesUseCase.Output> {
    sealed class Output {
        data class Success(val savedArticleIds: Set<Int>) : Output()
        object Failure : Output()
    }
}
