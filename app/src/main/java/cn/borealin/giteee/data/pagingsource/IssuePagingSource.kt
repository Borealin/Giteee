/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:10
 */

package cn.borealin.giteee.data.pagingsource

import androidx.paging.PagingSource
import cn.borealin.giteee.api.interfaces.IssueApi
import cn.borealin.giteee.api.interfaces.SearchApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.model.issue.IssueListItemData
import cn.borealin.giteee.ui.issue.IssueListType
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class IssuePagingSource(
    private val issueApi: IssueApi,
    private val searchApi: SearchApi,
    private val issueListType: IssueListType,
    private val userPreference: UserPreference
) : PagingSource<Int, IssueListItemData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IssueListItemData> {
        val token = userPreference.accountToken.first()
        val position = params.key ?: 1
        return try {
            val issueList = when (issueListType) {
                is IssueListType.My -> issueApi.getIssue(
                    token,
                    position,
                    params.loadSize
                )
                is IssueListType.Search -> searchApi.searchIssue(
                    token,
                    issueListType.username,
                    position,
                    params.loadSize
                )
            }

            val data = issueList.map {
                it.toIssueListItemData()
            }
            LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}