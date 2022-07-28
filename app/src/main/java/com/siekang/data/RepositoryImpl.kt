package com.siekang.data


import com.siekang.data.local.DbImpl
import com.siekang.data.local.prefs.PrefsImpl
import com.siekang.data.remote.ApiImpl
import com.siekang.data.remote.dto.Question
import com.siekang.data.remote.dto.Translation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    dbImpl: DbImpl,
    apiImpl: ApiImpl,
    prefsImpl: PrefsImpl
) : IRepository {

    private var mDbImpl: DbImpl = dbImpl
    private var mApiImpl: ApiImpl = apiImpl
    private var mPrefsImpl: PrefsImpl = prefsImpl


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
    override suspend fun getTranslations(page: Int, size: Int): List<Translation> =
        mApiImpl.getTranslations(page, size)

    override suspend fun getQuestions(): List<Question>? = mApiImpl.getQuestions()


    /////////////////////////////////////
    //
    // PREFERENCES
    //
    /////////////////////////////////////
    override fun hasNextIndex(): Flow<Boolean> = mPrefsImpl.hasNextIndex()

    override suspend fun toggleNextIndex() = mPrefsImpl.toggleNextIndex()

    override fun getEmailPref(): Flow<String> = mPrefsImpl.getEmailPref()

    override suspend fun saveEmailPref(email: String) = mPrefsImpl.saveEmailPref(email)

    override fun getPasswordPref(): Flow<String> = mPrefsImpl.getPasswordPref()

    override suspend fun savePasswordPref(password: String) =
        mPrefsImpl.savePasswordPref(password)

    override fun isRememberCredentialsPref(): Flow<Boolean> =
        mPrefsImpl.isRememberCredentialsPref()

    override suspend fun saveRememberCredentialsPref(isChecked: Boolean) =
        mPrefsImpl.saveRememberCredentialsPref(isChecked)

    override fun getUserToken(): Flow<String> = mPrefsImpl.getUserToken()

    override suspend fun saveTokenPref(token: String) = mPrefsImpl.saveTokenPref(token)
}