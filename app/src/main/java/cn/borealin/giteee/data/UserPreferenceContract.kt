/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-21 下午3:36
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-21 下午3:36
 */

package cn.borealin.giteee.data

import androidx.datastore.preferences.core.preferencesKey

object UserPreferenceContract {
    const val KEY_SETTING_PREFERENCE = "setting_preference"
    val KEY_ACCOUNT_TOKEN = preferencesKey<String>("account_token")
    const val DEFAULT_ACCOUNT_TOKEN = "default_account_token"
    val KEY_REFRESH_TOKEN = preferencesKey<String>("refresh_token")
    const val DEFAULT_REFRESH_TOKEN = "default_refresh_token"
    val KEY_ACCOUNT_LOGIN_NAME = preferencesKey<String>("account_login_name")
    const val DEFAULT_ACCOUNT_LOGIN_NAME = "default_account_login_name"
    val KEY_ACCOUNT_NAME = preferencesKey<String>("account_name")
    const val DEFAULT_ACCOUNT_NAME = "default_account_name"
}