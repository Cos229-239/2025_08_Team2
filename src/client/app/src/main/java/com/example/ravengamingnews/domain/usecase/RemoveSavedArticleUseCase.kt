package com.example.ravengamingnews.domain.usecase

interface RemoveSavedArticleUseCase : UseCase<Int, RemoveSavedArticleUseCase.Output> {
    sealed class Output {
        data class Success(val savedArticles: Set<Int>) : Output()
        object Failure : Output()
    }
}
