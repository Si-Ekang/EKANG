package com.siekang.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Translation(
    @Json(name = "french") val french: String,
    @Json(name = "fang") val fang: String
)
