package com.mxpj.speedyart.di

import android.content.Context
import com.mxpj.speedyart.data.repository.AppThemeRepositoryImpl
import com.mxpj.speedyart.data.database.SpeedyArtDatabase
import com.mxpj.speedyart.data.database.dao.DifficultyDao
import com.mxpj.speedyart.data.database.dao.PackDao
import com.mxpj.speedyart.data.database.dao.PictureCompletionDao
import com.mxpj.speedyart.data.database.dao.PictureDao
import com.mxpj.speedyart.data.repository.PackRepositoryImpl
import com.mxpj.speedyart.data.repository.PictureCompletionRepositoryImpl
import com.mxpj.speedyart.data.repository.PictureRepositoryImpl
import com.mxpj.speedyart.domain.repository.AppThemeRepository
import com.mxpj.speedyart.domain.repository.PackRepository
import com.mxpj.speedyart.domain.repository.PictureCompletionRepository
import com.mxpj.speedyart.domain.repository.PictureRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindAppThemeRepository(appThemeRepository: AppThemeRepositoryImpl): AppThemeRepository

    @Binds
    abstract fun bindPackRepository(packRepository: PackRepositoryImpl): PackRepository

    @Binds
    abstract fun bindPictureRepository(pictureRepository: PictureRepositoryImpl): PictureRepository

    @Binds
    abstract fun bindPictureCompletionRepository(pictureCompletionRepository: PictureCompletionRepositoryImpl): PictureCompletionRepository

    companion object {

        @Provides
        fun providePictureDao(@ApplicationContext application: Context): PictureDao {
            return SpeedyArtDatabase.getInstance(application).pictureDao()
        }

        @Provides
        fun providePackDao(@ApplicationContext application: Context): PackDao {
            return SpeedyArtDatabase.getInstance(application).packDao()
        }

        @Provides
        fun providePictureCompletionDao(@ApplicationContext application: Context): PictureCompletionDao {
            return SpeedyArtDatabase.getInstance(application).pictureCompletionDao()
        }

        @Provides
        fun provideDifficultyDao(@ApplicationContext application: Context): DifficultyDao {
            return SpeedyArtDatabase.getInstance(application).difficultyDao()
        }
    }
}