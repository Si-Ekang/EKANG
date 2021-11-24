package com.siekang.data


import com.siekang.data.local.DbImpl
import com.siekang.data.remote.ApiImpl
import com.siekang.data.remote.dto.Question
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    dbImpl: DbImpl,
    apiImpl: ApiImpl
) : IRepository {

    private var mDbImpl: DbImpl = dbImpl
    private var mApiImpl: ApiImpl = apiImpl


    override fun deleteAll() {
        mDbImpl.deleteAll()
    }

    override suspend fun getQuestions(): List<Question>? = mApiImpl.getQuestions()


}