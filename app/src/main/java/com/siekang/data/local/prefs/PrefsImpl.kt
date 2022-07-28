package com.siekang.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.siekang.utils.Constants
import com.siekang.utils.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_SI_EKANG_NAME)

class PrefsImpl @Inject constructor(
    private val context: Context
) : IPrefs {

    override fun hasNextIndex() = context.dataStore.data.catch { exception ->
        // 1 On the first line, you access the data of DataStore.
        // This property returns a Flow.
        // Then you call catch() from the Flow API to handle any errors.
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) {
            // 2 In the lambda block, you check if the exception is an instance of IOException.
            // If it is, you catch the exception and return an empty instance of Preferences.
            // If the exception isn’t IOException, you rethrow it or handle it in a way that works for you.
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        // 3 Finally, map() returns a Flow which contains the results of applying
        // the given function to each value of the original Flow.
        // In your case, you get the data by using a certain key, the PreferencesKeys.NIGHT_MODE_KEY.
        // If the key isn’t set when you try to read the data it returns null.
        // You use the Elvis operator to handle this and return false instead.
        it[PreferencesKeys.DATASTORE_HAS_NEXT_INDEX] ?: false
    }

    override suspend fun toggleNextIndex() {
        context.dataStore.edit {
            it[PreferencesKeys.DATASTORE_HAS_NEXT_INDEX] =
                !(it[PreferencesKeys.DATASTORE_HAS_NEXT_INDEX] ?: false)
        }
    }

    override fun getEmailPref(): Flow<String> = context.dataStore.data.catch { exception ->
        // 1 On the first line, you access the data of DataStore.
        // This property returns a Flow.
        // Then you call catch() from the Flow API to handle any errors.
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) {
            // 2 In the lambda block, you check if the exception is an instance of IOException.
            // If it is, you catch the exception and return an empty instance of Preferences.
            // If the exception isn’t IOException, you rethrow it or handle it in a way that works for you.
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        // 3 Finally, map() returns a Flow which contains the results of applying
        // the given function to each value of the original Flow.
        // In your case, you get the data by using a certain key, the PreferencesKeys.NIGHT_MODE_KEY.
        // If the key isn’t set when you try to read the data it returns null.
        // You use the Elvis operator to handle this and return false instead.
        it[PreferencesKeys.DATASTORE_EMAIL_KEY] ?: ""
    }


    override suspend fun saveEmailPref(email: String) {
        context.dataStore.edit {
            it[PreferencesKeys.DATASTORE_EMAIL_KEY] = email
        }
    }

    override fun getPasswordPref(): Flow<String> = context.dataStore.data.catch { exception ->
        // 1 On the first line, you access the data of DataStore.
        // This property returns a Flow.
        // Then you call catch() from the Flow API to handle any errors.
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) {
            // 2 In the lambda block, you check if the exception is an instance of IOException.
            // If it is, you catch the exception and return an empty instance of Preferences.
            // If the exception isn’t IOException, you rethrow it or handle it in a way that works for you.
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        // 3 Finally, map() returns a Flow which contains the results of applying
        // the given function to each value of the original Flow.
        // In your case, you get the data by using a certain key, the PreferencesKeys.NIGHT_MODE_KEY.
        // If the key isn’t set when you try to read the data it returns null.
        // You use the Elvis operator to handle this and return false instead.
        it[PreferencesKeys.DATASTORE_PASSWORD_KEY] ?: ""
    }


    override suspend fun savePasswordPref(password: String) {
        context.dataStore.edit {
            it[PreferencesKeys.DATASTORE_PASSWORD_KEY] = password
        }
    }

    override fun isRememberCredentialsPref(): Flow<Boolean> =
        context.dataStore.data.catch { exception ->
            // 1 On the first line, you access the data of DataStore.
            // This property returns a Flow.
            // Then you call catch() from the Flow API to handle any errors.
            // dataStore.data throws an IOException if it can't read the data
            if (exception is IOException) {
                // 2 In the lambda block, you check if the exception is an instance of IOException.
                // If it is, you catch the exception and return an empty instance of Preferences.
                // If the exception isn’t IOException, you rethrow it or handle it in a way that works for you.
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            // 3 Finally, map() returns a Flow which contains the results of applying
            // the given function to each value of the original Flow.
            // In your case, you get the data by using a certain key, the PreferencesKeys.NIGHT_MODE_KEY.
            // If the key isn’t set when you try to read the data it returns null.
            // You use the Elvis operator to handle this and return false instead.
            it[PreferencesKeys.DATASTORE_REMEMBER_CREDENTIALS_KEY] ?: false
        }


    override suspend fun saveRememberCredentialsPref(isChecked: Boolean) {
        context.dataStore.edit {
            it[PreferencesKeys.DATASTORE_REMEMBER_CREDENTIALS_KEY] = isChecked
        }
    }

    override fun getUserToken(): Flow<String> = context.dataStore.data.catch { exception ->
        // 1 On the first line, you access the data of DataStore.
        // This property returns a Flow.
        // Then you call catch() from the Flow API to handle any errors.
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) {
            // 2 In the lambda block, you check if the exception is an instance of IOException.
            // If it is, you catch the exception and return an empty instance of Preferences.
            // If the exception isn’t IOException, you rethrow it or handle it in a way that works for you.
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        // 3 Finally, map() returns a Flow which contains the results of applying
        // the given function to each value of the original Flow.
        // In your case, you get the data by using a certain key, the PreferencesKeys.NIGHT_MODE_KEY.
        // If the key isn’t set when you try to read the data it returns null.
        // You use the Elvis operator to handle this and return false instead.
        it[PreferencesKeys.DATASTORE_TOKEN_KEY] ?: ""
    }

    override suspend fun saveTokenPref(token: String) {
        context.dataStore.edit {
            it[PreferencesKeys.DATASTORE_TOKEN_KEY] = token
        }
    }
}