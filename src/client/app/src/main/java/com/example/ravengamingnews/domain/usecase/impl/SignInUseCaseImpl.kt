package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.usecase.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthRepository
) : SignInUseCase {
    override suspend fun execute(input: SignInUseCase.Input): SignInUseCase.Output {
        return withContext(Dispatchers.IO) {
            val result = authenticationRepository.signIn(input.email, input.password)
            if (result) {
                SignInUseCase.Output.Success
            } else {
                SignInUseCase.Output.Failure
            }
        }
    }
}
