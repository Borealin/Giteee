/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:45
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:11
 */

package cn.borealin.giteee.data.repository

import androidx.paging.PagingData
import cn.borealin.giteee.api.Resource
import cn.borealin.giteee.model.users.ProfileListItemData
import cn.borealin.giteee.model.users.RawUserData
import cn.borealin.giteee.model.users.UserData
import cn.borealin.giteee.ui.profile.ProfileListType
import cn.borealin.giteee.ui.profile.ProfileType
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getCurrentProfile(): Flow<Resource<RawUserData>>
    fun getProfile(profileType: ProfileType): Flow<Resource<UserData>>
    fun getList(profileListType: ProfileListType): Flow<PagingData<ProfileListItemData>>
    suspend fun checkLocalUsername(): String
}