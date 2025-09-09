package com.example.ravengamingnews.domain.usecase.impl

import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.usecase.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthRepository
) : SignUpUseCase {
    override suspend fun execute(input: SignUpUseCase.Input): SignUpUseCase.Output =
        withContext(Dispatchers.IO) {
            val result = authenticationRepository.signUp(input.email, input.password)
            if (result) {
                SignUpUseCase.Output.Success
            } else {
                SignUpUseCase.Output.Failure
            }
        }
}
