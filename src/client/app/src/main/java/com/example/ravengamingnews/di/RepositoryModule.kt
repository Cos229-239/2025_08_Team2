package com.example.ravengamingnews.di

import com.example.ravengamingnews.data.ArticleRepository
import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.data.repository.impl.ArticleRepositoryImpl
import com.example.ravengamingnews.data.repository.impl.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindArticleRepository(
        articleRepositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository

    @Binds
    @Singleton
    abstract fun bindAuthenticateRepository(impl: AuthRepositoryImpl): AuthRepository
}
