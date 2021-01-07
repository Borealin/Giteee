/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午12:33
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午12:33
 */

package cn.borealin.giteee.data.pagingsource

import androidx.paging.PagingSource
import cn.borealin.giteee.api.interfaces.RepositoryApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.model.common.RepositoryListItemData
import cn.borealin.giteee.ui.repository.RepositoryListType
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class RepositoryPagingSource(
    private val repositoryApi: RepositoryApi,
    private val userPreference: UserPreference,
    private val listType: RepositoryListType
) : PagingSource<Int, RepositoryListItemData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryListItemData> {
        val token = userPreference.accountToken.first()
        val position = params.key ?: 1
        return try {
            val repositoryList =
                when (listType) {
                    is RepositoryListType.MyRepository -> repositoryApi.getCurrentRepository(
                        token,
                        position,
                        params.loadSize
                    )
                    is RepositoryListType.PublicRepository -> repositoryApi.getUserRepository(
                        listType.username,
                        token,
                        position,
                        params.loadSize
                    )
                    is RepositoryListType.Watch -> repositoryApi.getUserWatchRepository(
                        listType.username,
                        token,
                        position,
                        params.loadSize
                    )
                    is RepositoryListType.Star -> repositoryApi.getUserStarRepository(
                        listType.username,
                        token,
                        position,
                        params.loadSize
                    )
                    is RepositoryListType.OrganizationRepository -> repositoryApi.getOrganizationRepository(
                        listType.username,
                        token,
                        position,
                        params.loadSize
                    )
                }
            val data = repositoryList.map {
                it.toRepositoryListItemData()
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