/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-21 下午3:02
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-21 下午3:02
 */

package cn.borealin.giteee.ui.entrance

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cn.borealin.giteee.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn


class EntranceViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    val requireLogin = loginRepository.requireLogin().flowOn(Dispatchers.IO).asLiveData()
}