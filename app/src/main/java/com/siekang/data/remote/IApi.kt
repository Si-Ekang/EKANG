package com.siekang.data.remote

import com.siekang.data.remote.dto.Question
import com.siekang.data.remote.dto.TranslationsResponse

interface IApi {
    suspend fun getTranslations(page: Int, size: Int): TranslationsResponse

    suspend fun getQuestions(): List<Question>?
}