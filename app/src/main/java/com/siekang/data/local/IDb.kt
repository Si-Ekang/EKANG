package com.siekang.data.local

interface IDb {

    suspend fun getWordTranslation(word: String): List<String>

    fun deleteAll()
}