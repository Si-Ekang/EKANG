package com.siekang.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "title")
    val title: String,
    @Json(name = "answer")
    val answer: String,
    @Json(name = "suggestions")
    val suggestions: List<String>
)
