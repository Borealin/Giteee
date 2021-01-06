/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午1:27
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午1:27
 */

package cn.borealin.giteee.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import cn.borealin.giteee.data.repository.LoginRepository
import cn.borealin.giteee.ui.common.HomeMenuType

class HomeViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _homeMenuList = listOf(
        HomeMenuType.Issue(-1),
        HomeMenuType.PullRequest(-1),
        HomeMenuType.Repository(-1),
        HomeMenuType.Organization(-1),
        HomeMenuType.Gists(-1)
    )

    val homeMenuList: List<HomeMenuType> = _homeMenuList
}