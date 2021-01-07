/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:07
 */

package cn.borealin.giteee.data.pagingsource

import androidx.paging.PagingSource
import cn.borealin.giteee.api.interfaces.ActivityApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.model.users.UserEventType
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class EventPagingSource(
    private val activityApi: ActivityApi,
    private val userPreference: UserPreference,
    private val username: String,
    private val received: Boolean,
    private val public: Boolean,
    private val isOrganization: Boolean
) : PagingSource<Int, UserEventType>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEventType> {
        val token = userPreference.accountToken.first()
        val position = params.key ?: 1
        return try {
            val event = if (isOrganization) {
                activityApi.getOrganizationEvent(
                    username,
                    token,
                    position,
                    params.loadSize
                )
            } else {
                if (received) {
                    if (public) {
                        activityApi.getUserReceivedPublicEvent(
                            username,
                            token,
                            position,
                            params.loadSize
                        )
                    } else {
                        activityApi.getUserReceivedEvent(username, token, position, params.loadSize)
                    }
                } else {
                    if (public) {
                        activityApi.getUserPublicEvent(username, token, position, params.loadSize)
                    } else {
                        activityApi.getUserEvent(username, token, position, params.loadSize)
                    }
                }
            }
            val data = event.map {
                it.toUserActionType()
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