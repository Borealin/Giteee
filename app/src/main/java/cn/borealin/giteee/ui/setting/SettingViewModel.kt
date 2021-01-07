/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午1:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午1:05
 */

package cn.borealin.giteee.ui.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import cn.borealin.giteee.data.repository.LoginRepository

class SettingViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    suspend fun logout() =
        loginRepository.logout()
}