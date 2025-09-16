package com.example.ravengamingnews.domain.usecase

interface UseCase<InputT, OutputT> {
    suspend fun execute(input: InputT): OutputT
}
