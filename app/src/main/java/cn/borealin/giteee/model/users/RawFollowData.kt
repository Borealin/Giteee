/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午12:26
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午12:26
 */

package cn.borealin.giteee.model.users

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawFollowData(
    @Expose
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @Expose
    @SerializedName("events_url")
    var eventsUrl: String,
    @Expose
    @SerializedName("followers_url")
    var followersUrl: String,
    @Expose
    @SerializedName("following_url")
    var followingUrl: String,
    @Expose
    @SerializedName("gists_url")
    var gistsUrl: String,
    @Expose
    @SerializedName("html_url")
    var htmlUrl: String,
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
    @SerializedName("organizations_url")
    var organizationsUrl: String,
    @Expose
    @SerializedName("received_events_url")
    var receivedEventsUrl: String,
    @Expose
    @SerializedName("repos_url")
    var reposUrl: String,
    @Expose
    @SerializedName("starred_url")
    var starredUrl: String,
    @Expose
    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String,
    @Expose
    @SerializedName("type")
    var type: String,
    @Expose
    @SerializedName("url")
    var url: String,
) : Parcelable, ProfileListConverter {
    override fun toProfileListItemData(): ProfileListItemData {
        return ProfileListItemData(
            name = name,
            login = login,
            avatarUrl = avatarUrl
        )
    }
}