/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午4:30
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午4:30
 */

package cn.borealin.giteee.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cn.borealin.giteee.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class LoginViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {
    fun getToken(code: String) = repository.getToken(code).flowOn(Dispatchers.IO).asLiveData()
}