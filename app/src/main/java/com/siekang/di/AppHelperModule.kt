package com.siekang.di

import android.content.Context
import com.siekang.data.IRepository
import com.siekang.data.RepositoryImpl
import com.siekang.data.local.DbImpl
import com.siekang.data.local.SiEkangDatabase
import com.siekang.data.local.prefs.PrefsImpl
import com.siekang.data.remote.ApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//@InstallIn(ViewModelComponent::class) // this is new
@InstallIn(SingletonComponent::class)
object AppHelperModule {

    @Provides
    fun provideDbHelper(appDatabase: SiEkangDatabase) =
        DbImpl(
            appDatabase.getUserDao(),
            appDatabase.getWordDao()
        )

    @Provides
    fun provideApiHelper() =
        ApiImpl(
            ApiModule.provideSiEkangApiService()
        )

    @Provides
    fun providePreferences(@ApplicationContext appContext: Context) =
        PrefsImpl(appContext)

    @Provides
//    @ViewModelScoped // this is new
    @Singleton
    fun provideRepository(dbImpl: DbImpl, apiImpl: ApiImpl, prefsImpl: PrefsImpl) =
        RepositoryImpl(dbImpl, apiImpl, prefsImpl) as IRepository
}