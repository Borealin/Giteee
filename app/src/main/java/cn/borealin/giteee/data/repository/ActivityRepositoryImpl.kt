/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:47
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:47
 */

package cn.borealin.giteee.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cn.borealin.giteee.api.interfaces.ActivityApi
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.data.pagingsource.EventPagingSource
import cn.borealin.giteee.ui.common.UserEventType
import kotlinx.coroutines.flow.Flow

class ActivityRepositoryImpl(
    private val activityApi: ActivityApi,
    private val userPreference: UserPreference,
    private val pageConfig: PagingConfig
) : ActivityRepository {
    override fun getEvent(username: String): Flow<PagingData<UserEventType>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                EventPagingSource(
                    activityApi,
                    userPreference,
                    username,
                    false
                )
            }
        ).flow
    }

    override fun getReceivedEvent(username: String): Flow<PagingData<UserEventType>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { EventPagingSource(activityApi, userPreference, username, true) }
        ).flow
    }
}