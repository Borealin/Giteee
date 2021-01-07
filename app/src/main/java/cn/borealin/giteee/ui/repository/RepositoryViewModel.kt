/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午4:02
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午4:02
 */

package cn.borealin.giteee.ui.repository

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.borealin.giteee.data.repository.RepositoryRepository
import cn.borealin.giteee.model.repository.RepositoryListItemData
import kotlinx.coroutines.flow.Flow

class RepositoryViewModel @ViewModelInject constructor(
    private val repositoryRepository: RepositoryRepository
) : ViewModel() {
    fun getRepoList(repositoryListType: RepositoryListType): Flow<PagingData<RepositoryListItemData>> {
        return repositoryRepository.getList(repositoryListType).cachedIn(viewModelScope)
    }
}