/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/3 下午11:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/3 下午11:44
 */

package cn.borealin.giteee.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.borealin.giteee.api.Resource
import cn.borealin.giteee.api.doFailure
import cn.borealin.giteee.api.doSuccess
import cn.borealin.giteee.data.repository.ActivityRepository
import cn.borealin.giteee.data.repository.LoginRepository
import cn.borealin.giteee.data.repository.ProfileRepository
import cn.borealin.giteee.model.common.HomeMenuType
import cn.borealin.giteee.model.common.ProfileListItemData
import cn.borealin.giteee.model.common.UserEventType
import cn.borealin.giteee.model.users.ProfileDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class ProfileViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val profileRepository: ProfileRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _profile = MutableLiveData<ProfileDetail>()

    val profile: LiveData<ProfileDetail> = _profile

    fun getUserProfile(profileType: ProfileType? = null) = liveData {
        val checkProfileType =
            profileType ?: ProfileType.User(profileRepository.checkLocalUsername())
        profileRepository.getProfile(checkProfileType).collectLatest { result ->
            result.doSuccess {
                _profile.postValue(it.toProfileDetail())
                _profileMenuList.postValue(
                    it.toHomeMenuList(checkProfileType)
                )
                emit(Resource.Success(it))
            }
            result.doFailure {
                emit(Resource.Failure(it))
            }
        }
    }


    fun getPublicEvents(username: String): Flow<PagingData<UserEventType>> {
        return activityRepository.getPublicEvent(username)
            .cachedIn(viewModelScope)
    }

    suspend fun getEvents(username: String? = null): Flow<PagingData<UserEventType>> {
        val checkUsername = username ?: profileRepository.checkLocalUsername()
        return activityRepository.getEvent(checkUsername)
            .cachedIn(viewModelScope)
    }

    fun getOrganizationEvents(username: String): Flow<PagingData<UserEventType>> {
        return activityRepository.getOrganizationEvent(username)
            .cachedIn(viewModelScope)
    }

    fun getList(profileListType: ProfileListType): Flow<PagingData<ProfileListItemData>> {
        return profileRepository.getList(profileListType)
            .cachedIn(viewModelScope)
    }

    val localName = flow {
        emit(profileRepository.checkLocalUsername())
    }.asLiveData()

    private val _profileMenuList = MutableLiveData<List<HomeMenuType>>().apply {
        postValue(
            listOf(
                HomeMenuType.Repository(-1),
                HomeMenuType.Organization(-1),
                HomeMenuType.Gists(-1),
                HomeMenuType.Star(-1),
                HomeMenuType.Watch(-1)
            )
        )
    }

    val profileMenuList: LiveData<List<HomeMenuType>> = _profileMenuList

}