/*
 * Created by Borealin (308704199deniel@gmail.com) on 2020/12/31 下午11:30
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 2020/12/31 下午11:30
 */

package cn.borealin.giteee.data

import androidx.paging.PagingConfig
import cn.borealin.giteee.api.Resource
import cn.borealin.giteee.api.interfaces.GiteeApi
import cn.borealin.giteee.api.interfaces.OAuthApi
import cn.borealin.giteee.ui.auth.LoginContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val oAuthApi: OAuthApi,
    private val giteeApi: GiteeApi,
    private val userPreference: UserPreference,
    private val pageConfig: PagingConfig
) : Repository {
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
                    setAccountToken(result.accessToken)
                    setRefreshToken(result.refreshToken)
                    emit(false)
                } catch (e: Exception) {
                    emit(true)
                }
            }
        }


    override val accountToken: Flow<String>
        get() = userPreference.accountToken

    override suspend fun setAccountToken(value: String) {
        userPreference.setAccountToken(value)
    }


    override val refreshToken: Flow<String>
        get() = userPreference.refreshToken

    override suspend fun setRefreshToken(value: String) {
        userPreference.setRefreshToken(value)
    }

    override fun getToken(code: String) = flow {
        try {
            val result = oAuthApi.getToken(
                code,
                LoginContract.CLIENT_ID,
                LoginContract.CALLBACK,
                LoginContract.CLIENT_SECRET
            )
            setAccountToken(result.accessToken)
            setRefreshToken(result.refreshToken)
            emit(Resource.success(result.accessToken))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error("Network Error", null))
        }
    }
}