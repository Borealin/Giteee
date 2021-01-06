/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午1:27
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午1:27
 */

package cn.borealin.giteee.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cn.borealin.giteee.data.repository.ProfileRepository
import cn.borealin.giteee.model.common.HomeMenuType
import kotlinx.coroutines.flow.flow

class HomeViewModel @ViewModelInject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _homeMenuList = listOf(
        HomeMenuType.Issue(-1),
        HomeMenuType.PullRequest(-1),
        HomeMenuType.Repository(-1),
        HomeMenuType.Organization(-1),
        HomeMenuType.Gists(-1)
    )

    val homeMenuList: List<HomeMenuType> = _homeMenuList
    val localName = flow {
        emit(profileRepository.checkLocalUsername())
    }.asLiveData()
}