/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午1:01
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午1:01
 */

package cn.borealin.giteee.ui.notifications

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.borealin.giteee.data.repository.ActivityRepository
import cn.borealin.giteee.data.repository.ProfileRepository
import cn.borealin.giteee.model.common.UserEventType
import kotlinx.coroutines.flow.Flow

class NotificationsViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    suspend fun getEvents(username: String? = null): Flow<PagingData<UserEventType>> {
        val checkUsername = username ?: profileRepository.checkLocalUsername()
        return activityRepository.getReceivedEvent(checkUsername)
            .cachedIn(viewModelScope)
    }
}