package com.alexon.domain.di

import com.alexon.domain.repositories.AuthRepository
import com.alexon.domain.usecase.auth.AuthUserUseCase
import com.alexon.domain.usecase.auth.AuthUserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideAuthUserUseCase(authRepository: AuthRepository): AuthUserUseCase {
        return AuthUserUseCaseImpl(authRepository)
    }
}