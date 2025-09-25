package com.example.ravengamingnews.domain.usecase

interface SaveArticleUseCase : UseCase<Int, SaveArticleUseCase.Output> {
    sealed class Output {
        data class Success(val savedArticles: Set<Int>) : Output()
        object Failure : Output()
    }
}
