package com.siekang.data.local.prefs

import kotlinx.coroutines.flow.Flow

interface IPrefs {
    fun hasNextIndex(): Flow<Boolean>
    suspend fun toggleNextIndex()

    fun getEmailPref(): Flow<String>
    suspend fun saveEmailPref(email: String)
    fun getPasswordPref(): Flow<String>
    suspend fun savePasswordPref(password: String)
    fun isRememberCredentialsPref(): Flow<Boolean>
    suspend fun saveRememberCredentialsPref(isChecked: Boolean)
    fun getUserToken(): Flow<String>
    suspend fun saveTokenPref(token: String)
}