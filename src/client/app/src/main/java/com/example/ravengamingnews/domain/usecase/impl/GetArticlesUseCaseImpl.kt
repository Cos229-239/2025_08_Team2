package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.ArticleRepository
import com.example.ravengamingnews.data.ArticleDto
import com.example.ravengamingnews.domain.model.Article
import com.example.ravengamingnews.domain.usecase.GetArticlesUseCase
import com.example.ravengamingnews.util.ResourceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Instant

class GetArticlesUseCaseImpl @Inject constructor(
    private val articleRepository: ArticleRepository,
) : GetArticlesUseCase {
    override suspend fun execute(input: Unit): GetArticlesUseCase.Output =
        withContext(Dispatchers.IO) {
            val result = articleRepository.getArticles()
            return@withContext result?.let { it ->
                GetArticlesUseCase.Output.Success(
                    articles = it.map { it.asDomainModel() }
                )
            } ?: GetArticlesUseCase.Output.Failure
        }

    private fun ArticleDto.asDomainModel() = Article(
        id = this.id,
        title = this.title,
        summary = this.summary,
        content = this.content,
        author = this.author,
        date = Instant.Companion.parse(this.date),
        gameId = this.game.id,
        gameNameResId = ResourceMapper.getGameNameResourceId(this.game.id),
        topic = this.topic,
        topicNameResId = this.topic?.let { ResourceMapper.getTopicNameResourceId(it) }
    )
}
