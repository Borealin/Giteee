/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午12:34
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午12:34
 */

package cn.borealin.giteee.api.interfaces

import cn.borealin.giteee.model.activity.RawUserEventData
import retrofit2.http.GET
import retrofit2.http.Query

interface IssueApi {
    @GET("user/issues")
    suspend fun getIssue(
        @Query("access_token") access_token: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<RawUserEventData>

}