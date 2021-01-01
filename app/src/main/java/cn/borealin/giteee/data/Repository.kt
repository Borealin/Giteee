/*
 * Created by Borealin (308704199deniel@gmail.com) on 2020/12/31 下午11:33
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 2020/12/31 下午11:33
 */

package cn.borealin.giteee.data

import cn.borealin.giteee.api.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun requireLogin(): Flow<Boolean>
    val accountToken: Flow<String>
    suspend fun setAccountToken(value: String)
    val refreshToken: Flow<String>
    suspend fun setRefreshToken(value: String)
    fun getToken(code: String): Flow<Resource<String?>>
}