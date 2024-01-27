package com.graduation.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
