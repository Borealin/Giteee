/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:45
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:11
 */

package cn.borealin.giteee.data.repository

import cn.borealin.giteee.api.Resource
import cn.borealin.giteee.model.users.UserData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getCurrentProfile(): Flow<Resource<UserData>>
    fun getProfile(username: String): Flow<Resource<UserData>>
}