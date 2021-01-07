/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午4:03
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午4:03
 */

package cn.borealin.giteee.data.repository

import androidx.paging.PagingData
import cn.borealin.giteee.model.repository.RepositoryListItemData
import cn.borealin.giteee.ui.repository.RepositoryListType
import kotlinx.coroutines.flow.Flow

interface RepositoryRepository {
    fun getList(repositoryListType: RepositoryListType): Flow<PagingData<RepositoryListItemData>>
}