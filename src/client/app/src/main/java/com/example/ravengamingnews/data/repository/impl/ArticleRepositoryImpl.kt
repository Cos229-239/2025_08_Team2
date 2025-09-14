package com.example.ravengamingnews.data.repository.impl

import com.example.ravengamingnews.data.ArticleDto
import com.example.ravengamingnews.data.ArticleRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ArticleRepository {
    override suspend fun getArticles(): List<ArticleDto> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("articles")
                .select(
                    Columns.raw(
                        """
                            *,
                            game:games(
                                id,
                                created_at,
                                name
                            )
                        """
                    )
                ) {
                    order("date", Order.DESCENDING)
                }
                .decodeList<ArticleDto>()
            result
        }
    }
}
