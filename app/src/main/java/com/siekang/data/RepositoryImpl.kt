package com.siekang.data


import com.siekang.data.local.DbImpl
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    dbImpl: DbImpl/*,
    apiImpl: ApiImpl*/
) : IRepository {

    private var mDbImpl: DbImpl = dbImpl
    //private var mApiImpl: ApiImpl = apiImpl


    override fun deleteAll() {
        mDbImpl.deleteAll()
    }

}