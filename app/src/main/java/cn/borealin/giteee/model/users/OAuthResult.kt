/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午4:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午4:43
 */

package cn.borealin.giteee.model.users

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OAuthResult(
    @Expose
    @SerializedName("access_token")
    val accessToken: String,
    @Expose
    @SerializedName("token_type")
    val tokenType: String,
    @Expose
    @SerializedName("expires_in")
    val expiresIn: Int,
    @Expose
    @SerializedName("refresh_token")
    val refreshToken: String,
    @Expose
    @SerializedName("scope")
    val scope: String,
    @Expose
    @SerializedName("created_at")
    val createAt: Int
) : Parcelable
