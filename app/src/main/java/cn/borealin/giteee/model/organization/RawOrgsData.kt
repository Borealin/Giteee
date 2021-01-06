/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午2:23
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午2:23
 */

package cn.borealin.giteee.model.organization

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawOrgsData(
    @Expose
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @Expose
    @SerializedName("description")
    var description: String,
    @Expose
    @SerializedName("events_url")
    var eventsUrl: String,
    @Expose
    @SerializedName("follow_count")
    var followCount: Int,
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("login")
    var login: String,
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("members_url")
    var membersUrl: String,
    @Expose
    @SerializedName("repos_url")
    var reposUrl: String,
    @Expose
    @SerializedName("url")
    var url: String,
    @Expose
    @SerializedName("email")
    var email: String,
    @Expose
    @SerializedName("public_repos")
    var publicRepos: Int,
    @Expose
    @SerializedName("members")
    var members: Int

) : Parcelable