/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午4:04
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午4:04
 */

package cn.borealin.giteee.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cn.borealin.giteee.api.interfaces.RepositoryApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.data.pagingsource.RepositoryPagingSource
import cn.borealin.giteee.model.common.RepositoryListItemData
import cn.borealin.giteee.ui.repository.RepositoryListType
import kotlinx.coroutines.flow.Flow

class RepositoryRepositoryImpl(
    private val repositoryApi: RepositoryApi,
    private val userPreference: UserPreference,
    private val pageConfig: PagingConfig
) : RepositoryRepository {
    override fun getList(repositoryListType: RepositoryListType): Flow<PagingData<RepositoryListItemData>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                RepositoryPagingSource(
                    repositoryApi,
                    userPreference,
                    repositoryListType
                )
            }
        ).flow
    }
}