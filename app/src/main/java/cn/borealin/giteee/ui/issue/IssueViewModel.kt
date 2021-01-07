/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:29
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:29
 */

package cn.borealin.giteee.ui.issue

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.borealin.giteee.data.repository.IssueRepository
import cn.borealin.giteee.model.issue.IssueListItemData
import kotlinx.coroutines.flow.Flow

class IssueViewModel @ViewModelInject constructor(
    private val issueRepository: IssueRepository
) : ViewModel() {
    fun getIssueList(): Flow<PagingData<IssueListItemData>> {
        return issueRepository.getIssueList().cachedIn(viewModelScope)
    }
}