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
import cn.borealin.giteee.data.UserPreferenceContract
import cn.borealin.giteee.data.repository.ActivityRepository
import cn.borealin.giteee.data.repository.LoginRepository
import cn.borealin.giteee.data.repository.ProfileRepository
import cn.borealin.giteee.model.users.ProfileDetail
import cn.borealin.giteee.ui.common.HomeMenuType
import cn.borealin.giteee.ui.common.UserEventType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

class ProfileViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val profileRepository: ProfileRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _profile = MutableLiveData<ProfileDetail>()

    val profile: LiveData<ProfileDetail> = _profile

    fun getCurrentProfile() = liveData {
        profileRepository.getCurrentProfile().collectLatest { result ->
            result.doSuccess {
                _profile.postValue(it.toProfileDetail())
                _profileMenuList.postValue(
                    it.toHomeMenuList()
                )
                emit(Resource.Success(it))
            }
            result.doFailure {
                emit(Resource.Failure(it))
            }
        }
    }

    suspend fun getEvents(username: String? = null): Flow<PagingData<UserEventType>> {
        val localUsername = loginRepository.accountLoginName.first()
        if (localUsername == UserPreferenceContract.DEFAULT_ACCOUNT_LOGIN_NAME) {
            profileRepository.getCurrentProfile().first()
        }
        val checkUsername = username ?: loginRepository.accountName.first()
        return activityRepository.getEvent(checkUsername)
            .cachedIn(viewModelScope)
    }

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