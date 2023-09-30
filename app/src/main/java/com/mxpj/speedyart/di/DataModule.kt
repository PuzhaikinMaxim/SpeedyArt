package com.mxpj.speedyart.di

import com.mxpj.speedyart.data.AppThemeRepositoryImpl
import com.mxpj.speedyart.domain.AppThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindAppThemeRepository(appThemeRepository: AppThemeRepositoryImpl): AppThemeRepository
}