/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:10
 */

package cn.borealin.giteee.data.repository

import androidx.paging.PagingData
import cn.borealin.giteee.model.issue.IssueListItemData
import kotlinx.coroutines.flow.Flow

interface IssueRepository {
    fun getIssueList(): Flow<PagingData<IssueListItemData>>
}