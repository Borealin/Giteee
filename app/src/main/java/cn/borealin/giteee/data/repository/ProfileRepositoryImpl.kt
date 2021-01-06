/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午1:36
 */

package cn.borealin.giteee.data.repository

import androidx.paging.PagingConfig
import cn.borealin.giteee.api.Resource
import cn.borealin.giteee.api.interfaces.ProfileApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.data.UserPreferenceContract
import cn.borealin.giteee.model.users.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val userPreference: UserPreference,
    val pageConfig: PagingConfig
) : ProfileRepository {
    override fun getCurrentProfile() = flow {
        val token = userPreference.accountToken.first()
        val accountName = userPreference.accountName.first()
        val accountLoginName = userPreference.accountLoginName.first()
        emit(Resource.Success(UserData.fromAccountName(accountName, accountLoginName)))
        try {
            val userProfile = profileApi.getCurrentProfile(token)
            val userOrganization = profileApi.getCurrentOrganizations(token, null, null)
            userPreference.setAccountName(userProfile.name)
            userPreference.setAccountLoginName(userProfile.login)
            emit(Resource.Success(UserData.fromRawUserData(userProfile, userOrganization.size)))
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause))
        }
    }.flowOn(Dispatchers.IO)

    override fun getProfile(username: String) = flow {
        val token = userPreference.accountToken.first()
        emit(
            Resource.Success(
                UserData.fromAccountName(
                    UserPreferenceContract.DEFAULT_ACCOUNT_NAME,
                    UserPreferenceContract.DEFAULT_ACCOUNT_LOGIN_NAME
                )
            )
        )
        try {
            val userProfile = profileApi.getUserProfile(username, token)
            val userOrganization = profileApi.getUserOrganizations(username, token, null, null)
            emit(Resource.Success(UserData.fromRawUserData(userProfile, userOrganization.size)))
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause))
        }
    }.flowOn(Dispatchers.IO)
}