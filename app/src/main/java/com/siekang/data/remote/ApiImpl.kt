package com.siekang.data.remote

import com.siekang.data.remote.api.SiEkangApiService
import com.siekang.data.remote.dto.Question
import com.siekang.data.remote.dto.Translation
import javax.inject.Inject

class ApiImpl @Inject constructor(
    siEkangApiService: SiEkangApiService
) : IApi {
    private var mSiEkangApiService: SiEkangApiService = siEkangApiService

    override suspend fun getTranslations(page: Int, size: Int): List<Translation> =
        mSiEkangApiService.getTranslations(page, size)

    override suspend fun getQuestions(): List<Question>? = mSiEkangApiService.getQuestions()
}