/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午1:27
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午1:27
 */

package cn.borealin.giteee.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cn.borealin.giteee.data.Repository
import cn.borealin.giteee.ui.common.HomeMenuType

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {
    val token = repository.accountToken.asLiveData()
    private val _homeMenuList = listOf(
        HomeMenuType.ISSUE,
        HomeMenuType.PULL_REQUEST,
        HomeMenuType.REPOSITORY,
        HomeMenuType.ORGANIZATION
    )

    val homeMenuList: List<HomeMenuType> = _homeMenuList
}