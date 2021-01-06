/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午12:33
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午12:33
 */

package cn.borealin.giteee.data.pagingsource

import androidx.paging.PagingSource
import cn.borealin.giteee.api.interfaces.ProfileApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.model.common.ProfileListItemData
import cn.borealin.giteee.ui.profile.ProfileListType
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class ProfilePagingSource(
    private val profileApi: ProfileApi,
    private val userPreference: UserPreference,
    private val listType: ProfileListType
) : PagingSource<Int, ProfileListItemData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProfileListItemData> {
        val token = userPreference.accountToken.first()
        val position = params.key ?: 1
        return try {
            val profileList = when (listType) {
                is ProfileListType.Follower -> profileApi.getUserFollower(
                    listType.username,
                    token,
                    position,
                    params.loadSize
                )
                is ProfileListType.Following -> profileApi.getUserFollowing(
                    listType.username,
                    token,
                    position,
                    params.loadSize
                )
                is ProfileListType.Organization -> profileApi.getUserOrganizations(
                    listType.username,
                    token,
                    position,
                    params.loadSize
                )
                is ProfileListType.Member -> profileApi.getOrganizationMember(
                    listType.username,
                    token,
                    position,
                    params.loadSize
                )
            }
            val data = profileList.map {
                it.toProfileListItemData()
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