package com.example.ravengamingnews.data.repository.impl

import com.example.ravengamingnews.data.ArticleWithGameDto
import com.example.ravengamingnews.data.ArticleRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ArticleRepository {
    override suspend fun getArticles(): List<ArticleWithGameDto> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("articles")
                .select(
                    Columns.raw(
                        """
                            *,
                            game:games(
                                id,
                                name,
                                created_at
                            )
                        """
                    )
                ).decodeList<ArticleWithGameDto>()
            result
        }
    }
}
