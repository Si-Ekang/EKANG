package com.siekang.data.local


import com.siekang.data.local.dao.UserDao
import com.siekang.data.local.dao.WordDao
import javax.inject.Inject

class DbImpl @Inject constructor(
    userDao: UserDao,
    wordDao: WordDao
) : IDb {

    private var mUserDao: UserDao = userDao
    private var mWordDao: WordDao = wordDao

    override suspend fun getWordTranslation(word: String): List<String> {
        return mWordDao.getTranslations(word)
    }

    override fun deleteAll() {
        mUserDao.deleteAll()
        mWordDao.deleteAll()
    }
}