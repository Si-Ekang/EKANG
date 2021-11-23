package com.siekang.di

import android.net.http.Headers
import com.siekang.data.local.bean.TimeOut
import com.siekang.data.remote.api.SiEkangApiService
import com.siekang.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    fun provideOkHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message: String -> Timber.tag("OkHttp").d(message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    /* Provide OkHttp for the app */
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TimeOut.TIME_OUT_READ.value.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TimeOut.TIME_OUT_CONNECTION.value.toLong(), TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                // Customize the request
                val request = original.newBuilder()
                    .header(Headers.CONTENT_TYPE, "application/json; charset=utf-8")
                    .header(Headers.CONN_DIRECTIVE, "close")
                    .header(Headers.ACCEPT_RANGES, "Identity")
                    .build()
                val response = chain.proceed(request)
                response.cacheResponse
                response
            })
            .addInterceptor(provideOkHttpLogger())
            .build()
    }


    /* Provide Retrofit for the app */
    @Provides
    fun provideRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(provideOkHttp())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @NotNull
    fun provideSiEkangApiService(): SiEkangApiService {
        return provideRetrofit(Constants.BASE_ENDPOINT_SI_EKANG)
            .create(SiEkangApiService::class.java)
    }
}