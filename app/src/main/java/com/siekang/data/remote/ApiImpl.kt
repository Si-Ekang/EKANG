package com.siekang.data.remote

import com.siekang.data.remote.api.SiEkangApiService
import com.siekang.data.remote.dto.Question
import javax.inject.Inject

class ApiImpl @Inject constructor(
    siEkangApiService: SiEkangApiService
) : IApi {
    private var mSiEkangApiService: SiEkangApiService = siEkangApiService

    override suspend fun getQuestions(): List<Question>? = mSiEkangApiService.getQuestions()
}