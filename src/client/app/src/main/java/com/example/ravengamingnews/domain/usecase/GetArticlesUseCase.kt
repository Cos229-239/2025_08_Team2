package com.example.ravengamingnews.domain.usecase

import com.example.ravengamingnews.domain.model.Article

interface GetArticlesUseCase : UseCase<Unit, GetArticlesUseCase.Output> {
    sealed class Output {
        data class Success(val articles: List<Article>) : Output()
        object Failure : Output()
    }
}
