/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午2:32
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午2:32
 */

package cn.borealin.giteee.api.interfaces

import cn.borealin.giteee.model.users.OAuthResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface OAuthApi {

    @FormUrlEncoded
    @POST("https://gitee.com/oauth/token?grant_type=authorization_code")
    suspend fun getToken(
        @Query("code") code: String,
        @Query("client_id") client_id: String,
        @Query("redirect_uri") redirect_uri: String,
        @Field("client_secret") client_secret: String
    ): OAuthResult

    @POST("https://gitee.com/oauth/token?grant_type=refresh_token")
    suspend fun refreshToken(
        @Query("refresh_token") token: String
    ): OAuthResult
}