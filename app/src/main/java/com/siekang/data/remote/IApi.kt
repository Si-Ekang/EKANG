package com.siekang.data.remote

import com.siekang.data.remote.dto.Question
import com.siekang.data.remote.dto.Translation

interface IApi {
    suspend fun getTranslations(page: Int, size: Int): List<Translation>

    suspend fun getQuestions(): List<Question>?
}