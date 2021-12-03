package com.siekang.data.remote.api

import com.siekang.data.remote.dto.Question
import com.siekang.data.remote.dto.Translation
import retrofit2.http.GET
import retrofit2.http.Query

interface SiEkangApiService {
    @GET("translations")
    suspend fun getTranslations(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<Translation>

    @GET("question")
    suspend fun getQuestions(): List<Question>?
}