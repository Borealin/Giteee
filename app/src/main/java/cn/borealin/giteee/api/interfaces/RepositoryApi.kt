/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午4:04
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午4:04
 */

package cn.borealin.giteee.api.interfaces

import cn.borealin.giteee.model.repository.RawRepositoryData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryApi {

    @GET("user/repos")
    suspend fun getCurrentRepository(
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawRepositoryData>

    @GET("users/{username}/repos")
    suspend fun getUserRepository(
        @Path("username") username: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawRepositoryData>

    @GET("orgs/{org}/repos")
    suspend fun getOrganizationRepository(
        @Path("org") org: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawRepositoryData>


    @GET("users/{username}/starred")
    suspend fun getUserStarRepository(
        @Path("username") username: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawRepositoryData>

    @GET("users/{username}/subscriptions")
    suspend fun getUserWatchRepository(
        @Path("username") username: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawRepositoryData>
}