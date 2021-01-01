/*
 * Created by Borealin (308704199deniel@gmail.com) on 2020/12/31 下午11:28
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 2020/12/30 上午11:29
 */

package cn.borealin.giteee.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

private suspend fun <T> DataStore<Preferences>.setValue(
    key: Preferences.Key<T>,
    value: T
) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

private fun <T> DataStore<Preferences>.getValueAsFlow(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> {
    return this.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[key] ?: defaultValue
    }
}

class UserPreference(context: Context) {
    private val dataStore =
        context.createDataStore(name = UserPreferenceContract.KEY_SETTING_PREFERENCE)

    private suspend fun <T> setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.setValue(key, value)
    }

    private fun <T> getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return dataStore.getValueAsFlow(key, defaultValue)
    }

    suspend fun clearPreferenceRepository() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun setAccountToken(token: String) {
        setValue(UserPreferenceContract.KEY_ACCOUNT_TOKEN, token)
    }

    val accountToken =
        getValueAsFlow(
            UserPreferenceContract.KEY_ACCOUNT_TOKEN,
            UserPreferenceContract.DEFAULT_ACCOUNT_TOKEN
        )

    suspend fun setRefreshToken(token: String) {
        setValue(UserPreferenceContract.KEY_REFRESH_TOKEN, token)
    }

    val refreshToken =
        getValueAsFlow(
            UserPreferenceContract.KEY_REFRESH_TOKEN,
            UserPreferenceContract.DEFAULT_REFRESH_TOKEN
        )

}