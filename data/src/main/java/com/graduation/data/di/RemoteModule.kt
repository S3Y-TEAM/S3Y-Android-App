package com.graduation.data.di

import com.graduation.core.keys.SecretKeysUtils
import com.graduation.data.remote.interceptors.HeadersSetupInterceptor
import com.graduation.data.remote.api.AuthApiService
import com.graduation.data.repositoryImpl.AuthRepositoryImpl
import com.graduation.domain.repositories.AuthRepository
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
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    /** Repositories **/

    @Provides
    fun provideAuthRepo(authApiService: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(authApiService)
    }
}