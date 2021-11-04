package com.siekang.di

import com.siekang.data.IRepository
import com.siekang.data.RepositoryImpl
import com.siekang.data.local.DbImpl
import com.siekang.data.local.SiEkangDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // this is new
object AppHelperModule {

    @Provides
    fun provideDbHelper(appDatabase: SiEkangDatabase) =
        DbImpl(
           appDatabase.getUserDao() /*,
            appDatabase.getWeatherDao()*/
        )

    /*@Provides
    fun provideApiHelper() =
        ApiImpl(
            ApiModule.provideArtistsAPIService(),
            ApiModule.provideGoogleAPIService(),
            ApiModule.provideYoutubeApiService(),
            ApiModule.provideWeatherApiService(),
            ApiModule.proWeatherBulkApiService()
        )*/

    @Provides
    @ViewModelScoped // this is new
    fun provideRepository(dbImpl: DbImpl/*, apiImpl: ApiImpl*/) =
        RepositoryImpl(dbImpl/*, apiImpl*/) as IRepository
}