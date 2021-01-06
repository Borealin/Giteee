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
import cn.borealin.giteee.ui.profile.ProfileType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val userPreference: UserPreference,
    private val pageConfig: PagingConfig
) : ProfileRepository {
    override fun getCurrentProfile() = flow {
        val token = userPreference.accountToken.first()
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

    override fun getProfile(profileType: ProfileType) = flow {
        val token = userPreference.accountToken.first()
        try {
            when (profileType) {
                is ProfileType.User -> {
                    val userProfile = profileApi.getUserProfile(profileType.username, token)
                    val userOrganization =
                        profileApi.getUserOrganizations(profileType.username, token, null, null)
                    emit(
                        Resource.Success(
                            UserData.fromRawUserData(
                                userProfile,
                                userOrganization.size
                            )
                        )
                    )
                }
                is ProfileType.Organization -> {
                    val userProfile = profileApi.getOrganization(profileType.username, token)
                    emit(Resource.Success(UserData.fromRawOrgsData(userProfile)))
                }
            }

        } catch (e: Exception) {
            emit(Resource.Failure(e.cause))
        }
    }.flowOn(Dispatchers.IO)

    override fun getList(profileListType: ProfileListType): Flow<PagingData<ProfileListItemData>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                ProfilePagingSource(
                    profileApi,
                    userPreference,
                    profileListType
                )
            }
        ).flow
    }

    override suspend fun checkLocalUsername(): String {
        val localUserLoginName = userPreference.accountLoginName.first()
        if (localUserLoginName == UserPreferenceContract.DEFAULT_ACCOUNT_LOGIN_NAME) {
            getCurrentProfile().collectLatest {

            }
        }
        return userPreference.accountLoginName.first()
    }
}