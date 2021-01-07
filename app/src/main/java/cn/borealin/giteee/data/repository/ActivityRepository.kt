/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:46
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:46
 */

package cn.borealin.giteee.data.repository

import androidx.paging.PagingData
import cn.borealin.giteee.model.users.UserEventType
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun getEvent(username: String): Flow<PagingData<UserEventType>>
    fun getPublicEvent(username: String): Flow<PagingData<UserEventType>>
    fun getReceivedEvent(username: String): Flow<PagingData<UserEventType>>
    fun getOrganizationEvent(username: String): Flow<PagingData<UserEventType>>
}