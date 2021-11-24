package com.siekang.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class TranslationsTypeConverter {

    @TypeConverter
    fun fromTranslationsList(translations: List<String?>?): String? {
        if (translations == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(translations, type)
    }

    @TypeConverter
    fun toTranslationsList(translationsString: String?): List<String?>? {
        if (translationsString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(translationsString, type)
    }
}