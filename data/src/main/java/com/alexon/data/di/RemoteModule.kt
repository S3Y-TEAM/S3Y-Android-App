package com.alexon.data.di

import com.alexon.core.constants.SecretKeysUtils
import com.alexon.data.remote.interceptors.HeadersSetupInterceptor
import com.alexon.data.remote.api.AuthApiService
import com.alexon.domain.repositories.AuthRepository
import com.alexon.domain.usecase.SendOtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val timeOut = 20L

    /** Retrofit **/

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headersSetupInterceptor: HeadersSetupInterceptor,
    ): OkHttpClient =
        OkHttpClient().newBuilder().apply {
            callTimeout(timeOut, TimeUnit.SECONDS)
            connectTimeout(timeOut, TimeUnit.SECONDS)
            readTimeout(timeOut, TimeUnit.SECONDS)
            writeTimeout(timeOut, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
            addInterceptor(headersSetupInterceptor)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(SecretKeysUtils.baseUrl())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /** APIs **/

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ) : SendOtpUseCase{
        return SendOtpUseCase(authRepository)
    }

}