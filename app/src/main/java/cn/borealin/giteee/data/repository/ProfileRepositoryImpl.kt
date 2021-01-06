/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午1:36
 */

package cn.borealin.giteee.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cn.borealin.giteee.api.Resource
import cn.borealin.giteee.api.interfaces.ProfileApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.data.UserPreferenceContract
import cn.borealin.giteee.data.pagingsource.ProfilePagingSource
import cn.borealin.giteee.model.common.ProfileListItemData
import cn.borealin.giteee.model.users.UserData
import cn.borealin.giteee.ui.profile.ProfileListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val userPreference: UserPreference,
    private val pageConfig: PagingConfig
) : ProfileRepository {
    override fun getCurrentProfile() = flow {
        val token = userPreference.accountToken.first()
        val accountName = userPreference.accountName.first()
        val accountLoginName = userPreference.accountLoginName.first()
        emit(Resource.Success(UserData.fromAccountName(accountName, accountLoginName)))
        try {
            val userProfile = profileApi.getCurrentProfile(token)
            val userOrganization =
                profileApi.getUserOrganizations(userProfile.login, token, null, null)
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

    override fun getFollower(username: String): Flow<PagingData<ProfileListItemData>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                ProfilePagingSource(
                    profileApi,
                    userPreference,
                    ProfileListType.Follower(username)
                )
            }
        ).flow
    }

    override fun getFollowing(username: String): Flow<PagingData<ProfileListItemData>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                ProfilePagingSource(
                    profileApi,
                    userPreference,
                    ProfileListType.Following(username)
                )
            }
        ).flow
    }

    override fun getOrganization(username: String): Flow<PagingData<ProfileListItemData>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                ProfilePagingSource(
                    profileApi,
                    userPreference,
                    ProfileListType.Organization(username)
                )
            }
        ).flow
    }

    override suspend fun checkLocalUsername(): String {
        val localUsername = userPreference.accountLoginName.first()
        if (localUsername == UserPreferenceContract.DEFAULT_ACCOUNT_LOGIN_NAME) {
            getCurrentProfile().first()
        }
        return userPreference.accountLoginName.first()
    }
}