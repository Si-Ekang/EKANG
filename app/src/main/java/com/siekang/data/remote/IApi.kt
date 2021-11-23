package com.siekang.data.remote

import com.siekang.data.remote.dto.Question

interface IApi {

    suspend fun getQuestions(): List<Question>?
}