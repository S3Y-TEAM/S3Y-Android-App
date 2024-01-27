package com.graduation.domain.di

import com.graduation.domain.repositories.AuthRepository
import com.graduation.domain.usecase.auth.AuthUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideAuthUserUseCase(authRepository: AuthRepository): AuthUserUseCase {
        return AuthUserUseCase(authRepository)
    }
}