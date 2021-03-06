package com.siekang.di

import android.content.Context
import androidx.room.Room
import com.siekang.data.local.SiEkangDatabase
import com.siekang.data.local.dao.UserDao
import com.siekang.data.local.dao.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): SiEkangDatabase {
        return Room
            .databaseBuilder(appContext, SiEkangDatabase::class.java, SiEkangDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(appDatabase: SiEkangDatabase): UserDao {
        return appDatabase.getUserDao()
    }

    @Provides
    fun provideWordDao(appDatabase: SiEkangDatabase): WordDao {
        return appDatabase.getWordDao()
    }
}