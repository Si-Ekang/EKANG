package com.siekang.data.remote.api

import com.siekang.data.remote.dto.Question
import retrofit2.http.GET

interface SiEkangApiService {
    @GET("question")
    suspend fun getQuestions(): List<Question>?
}