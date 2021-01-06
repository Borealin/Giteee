/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午7:38
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:11
 */

package cn.borealin.giteee.data.repository

import cn.borealin.giteee.api.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun requireLogin(): Flow<Boolean>
    val accountToken: Flow<String>
    val refreshToken: Flow<String>
    val accountName: Flow<String>
    val accountLoginName: Flow<String>
    fun getToken(code: String): Flow<Resource<String?>>
}