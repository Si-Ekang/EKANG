package com.siekang.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TranslationsResponse(
    val translations: List<Translation>
)
