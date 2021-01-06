/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 下午6:42
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 下午6:42
 */

package cn.borealin.giteee.api.interfaces

import cn.borealin.giteee.model.organization.RawOrgsData
import cn.borealin.giteee.model.organization.RawUserOrgsData
import cn.borealin.giteee.model.users.RawFollowData
import cn.borealin.giteee.model.users.RawUserData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {
    @GET("user")
    suspend fun getCurrentProfile(
        @Query("access_token") accessToken: String
    ): RawUserData

    @GET("users/{username}")
    suspend fun getUserProfile(
        @Path("username") username: String,
        @Query("access_token") accessToken: String
    ): RawUserData

    @GET("orgs/{org}")
    suspend fun getOrganization(
        @Path("org") org: String,
        @Query("access_token") accessToken: String
    ): RawOrgsData

    @GET("orgs/{org}/members")
    suspend fun getOrganizationMember(
        @Path("org") org: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawUserOrgsData>

    @GET("users/{username}/orgs")
    suspend fun getUserOrganizations(
        @Path("username") username: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawFollowData>

    @GET("users/{username}/followers")
    suspend fun getUserFollower(
        @Path("username") username: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawFollowData>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
        @Query("access_token") accessToken: String,
        @Query("page") page: Int?,
        @Query("per_page") pageSize: Int?
    ): List<RawFollowData>

}