/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午8:50
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午8:50
 */

package cn.borealin.giteee.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    val inputContent: MutableLiveData<String> = MutableLiveData<String>().apply {
        postValue("")
    }
    val inputContentStatic: LiveData<String> = inputContent
}