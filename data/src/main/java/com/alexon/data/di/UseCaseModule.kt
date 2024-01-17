package com.alexon.data.di

import com.alexon.domain.repositories.AuthRepository
import com.alexon.domain.usecase.SendOtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): SendOtpUseCase {
        return SendOtpUseCase(authRepository)
    }
}