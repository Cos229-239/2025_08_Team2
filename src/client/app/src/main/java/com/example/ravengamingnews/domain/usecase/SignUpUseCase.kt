package com.example.ravengamingnews.domain.usecase

import com.example.ravengamingnews.domain.model.UserSignUp

interface SignUpUseCase: UseCase<SignUpUseCase.Input, SignUpUseCase.Output> {
    class Input(val userData: UserSignUp)
    sealed class Output {
        object Success: Output()
        object Failure: Output()
    }
}
