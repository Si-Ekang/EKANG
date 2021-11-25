package com.siekang.data


import com.siekang.data.local.DbImpl
import com.siekang.data.remote.ApiImpl
import com.siekang.data.remote.dto.Question
import com.siekang.data.remote.dto.TranslationsResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    dbImpl: DbImpl,
    apiImpl: ApiImpl
) : IRepository {

    private var mDbImpl: DbImpl = dbImpl
    private var mApiImpl: ApiImpl = apiImpl


    /////////////////////////////////////
    //
    // DB
    //
    /////////////////////////////////////
    override suspend fun getWordTranslation(word: String): List<String> {
        return mDbImpl.getWordTranslation(word)
    }

    override fun deleteAll() {
        mDbImpl.deleteAll()
    }

    /////////////////////////////////////
    //
    // API
    //
    /////////////////////////////////////
    override suspend fun getTranslations(page: Int, size: Int): TranslationsResponse =
        mApiImpl.getTranslations(page, size)

    override suspend fun getQuestions(): List<Question>? = mApiImpl.getQuestions()


}