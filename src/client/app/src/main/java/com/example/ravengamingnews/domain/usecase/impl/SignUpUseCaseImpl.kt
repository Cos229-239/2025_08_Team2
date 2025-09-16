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
            val userData = input.userData
            val result = authenticationRepository.signUp(
                email = userData.email,
                password = userData.password,
                firstName = userData.firstName,
                lastName = userData.lastName,
                dateOfBirth = userData.dateOfBirth
            )
            if (result) {
                SignUpUseCase.Output.Success
            } else {
                SignUpUseCase.Output.Failure
            }
        }
}
