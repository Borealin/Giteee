/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 下午7:08
 */

package cn.borealin.giteee.data.repository

import cn.borealin.giteee.api.Resource
import cn.borealin.giteee.api.interfaces.OAuthApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.data.UserPreferenceContract
import cn.borealin.giteee.ui.auth.LoginContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val oAuthApi: OAuthApi,
    private val userPreference: UserPreference
) : LoginRepository {

    // Token About
    override fun requireLogin() =
        flow {
            val (accessToken, refreshToken) = userPreference.accountToken.combine(
                userPreference.refreshToken
            ) { a, b -> Pair(a, b) }.first()
            if (accessToken == UserPreferenceContract.DEFAULT_ACCOUNT_TOKEN) {
                emit(true)
            } else {
                try {
                    val result = oAuthApi.refreshToken(refreshToken)
                    userPreference.setAccountToken(result.accessToken)
                    userPreference.setRefreshToken(result.refreshToken)
                    emit(false)
                } catch (e: Exception) {
                    emit(true)
                }
            }
        }

    override val accountToken: Flow<String>
        get() = userPreference.accountToken

    override val refreshToken: Flow<String>
        get() = userPreference.refreshToken

    override val accountName: Flow<String>
        get() = userPreference.accountName

    override val accountLoginName: Flow<String>
        get() = userPreference.accountLoginName

    override fun getToken(code: String) = flow {
        try {
            val result = oAuthApi.getToken(
                code,
                LoginContract.CLIENT_ID,
                LoginContract.CALLBACK,
                LoginContract.CLIENT_SECRET
            )
            userPreference.setAccountToken(result.accessToken)
            userPreference.setRefreshToken(result.refreshToken)
            emit(Resource.Success(result.accessToken))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(e.cause))
        }
    }
}