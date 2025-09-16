package com.example.ravengamingnews.di

import com.example.ravengamingnews.domain.usecase.*
import com.example.ravengamingnews.domain.usecase.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetArticlesUseCase(impl: GetArticlesUseCaseImpl): GetArticlesUseCase

    @Binds
    abstract fun bindGetSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    abstract fun bindAuthenticateUseCase(impl: SignInUseCaseImpl): SignInUseCase
}
