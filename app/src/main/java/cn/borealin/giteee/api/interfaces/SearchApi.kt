/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午8:27
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午8:27
 */

package cn.borealin.giteee.api.interfaces

import cn.borealin.giteee.model.issue.RawIssueData
import cn.borealin.giteee.model.repository.RawRepositoryData
import cn.borealin.giteee.model.users.RawFollowData
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/repositories")
    suspend fun searchRepository(
        @Query("access_token") accessToken: String,
        @Query("q") q: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawRepositoryData>

    @GET("search/issues")
    suspend fun searchIssue(
        @Query("access_token") access_token: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<RawIssueData>

    @GET("search/users")
    suspend fun searchUser(
        @Query("access_token") accessToken: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<RawFollowData>


}