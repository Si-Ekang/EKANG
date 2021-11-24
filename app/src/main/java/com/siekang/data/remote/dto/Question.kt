package com.siekang.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    val id: String,
    val title: String,
    val answer: String,
    val suggestions: List<String>
)
