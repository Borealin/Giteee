/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:10
 */

package cn.borealin.giteee.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cn.borealin.giteee.api.interfaces.IssueApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.data.pagingsource.IssuePagingSource
import cn.borealin.giteee.model.issue.IssueListItemData
import kotlinx.coroutines.flow.Flow

class IssueRepositoryImpl(
    private val issueApi: IssueApi,
    private val userPreference: UserPreference,
    private val pageConfig: PagingConfig
) : IssueRepository {
    override fun getIssueList(): Flow<PagingData<IssueListItemData>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                IssuePagingSource(
                    issueApi,
                    userPreference
                )
            }
        ).flow
    }
}