/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午12:34
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午12:34
 */

package cn.borealin.giteee.api.interfaces

import cn.borealin.giteee.model.activity.UserEventData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivityApi {
    @GET("users/{username}/events")
    suspend fun getUserEvent(
        @Path("username") username: String,
        @Query("access_token") access_token: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<UserEventData>

    @GET("users/{username}/events/public")
    suspend fun getUserPublicEvent(
        @Path("username") username: String,
        @Query("access_token") access_token: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<UserEventData>

    @GET("users/{username}/received_events")
    suspend fun getUserReceivedEvent(
        @Path("username") username: String,
        @Query("access_token") access_token: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<UserEventData>

    @GET("users/{username}/received_events/public")
    suspend fun getUserReceivedPublicEvent(
        @Path("username") username: String,
        @Query("access_token") access_token: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<UserEventData>
}