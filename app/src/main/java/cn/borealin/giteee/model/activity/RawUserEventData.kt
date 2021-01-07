/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午1:12
 */

package cn.borealin.giteee.model.activity

import android.os.Parcelable
import cn.borealin.giteee.model.users.UserEventType
import cn.borealin.giteee.utils.TimeUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawUserEventData(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("type")
    val type: String?,
    @Expose
    @SerializedName("actor")
    val actor: UserEventActorData?,
    @Expose
    @SerializedName("repo")
    val repo: UserEventRepoData?,
    @Expose
    @SerializedName("public")
    val public: Boolean,
    @Expose
    @SerializedName("created_at")
    val createdAt: String,
    @Expose
    @SerializedName("payload")
    val payload: UserEventPayloadData?
) : Parcelable {
    fun toUserActionType(): UserEventType {
        return if (actor != null) {
            if (repo != null) {
                when (type) {
                    "PushEvent" -> UserEventType.Push(
                        actor.name,
                        repo.humanName,
                        actor.avatarUrl,
                        TimeUtils.fromTimeToRemaining(createdAt)
                    )
                    "CreateEvent" -> UserEventType.Create(
                        actor.name,
                        repo.humanName,
                        actor.avatarUrl,
                        TimeUtils.fromTimeToRemaining(createdAt)
                    )
                    "MemberEvent" -> UserEventType.Member(
                        actor.name,
                        repo.humanName,
                        actor.avatarUrl,
                        TimeUtils.fromTimeToRemaining(createdAt)
                    )
                    else -> UserEventType.Undefined(
                        operator = actor.name,
                        avatarUrl = actor.avatarUrl,
                        time = TimeUtils.fromTimeToRemaining(createdAt)
                    )
                }
            } else {
                UserEventType.Undefined(
                    operator = actor.name,
                    avatarUrl = actor.avatarUrl,
                    time = TimeUtils.fromTimeToRemaining(createdAt)
                )
            }
        } else {
            UserEventType.Undefined(
                operator = "Unknown",
                time = TimeUtils.fromTimeToRemaining(createdAt)
            )
        }
    }
}

@Parcelize
data class UserEventActorData(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @Expose
    @SerializedName("url")
    val url: String,
    @Expose
    @SerializedName("html_url")
    val htmlUrl: String,
) : Parcelable

@Parcelize
data class UserEventRepoData(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("full_name")
    val fullName: String,
    @Expose
    @SerializedName("human_name")
    val humanName: String,
    @Expose
    @SerializedName("url")
    val url: String,
    @Expose
    @SerializedName("namespace")
    val login: UserEventRepoNamespaceData
) : Parcelable

@Parcelize
data class UserEventRepoNamespaceData(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("type")
    val type: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("path")
    val path: String,
    @Expose
    @SerializedName("html_url")
    val htmlUrl: String,
) : Parcelable

@Parcelize
data class UserEventPayloadData(
    @Expose
    @SerializedName("ref_type")
    val ref_type: String?,
    @Expose
    @SerializedName("ref")
    val ref: String?,
    @Expose
    @SerializedName("default_branch")
    val defaultBranch: String?,
    @Expose
    @SerializedName("description")
    val description: String?,

    ) : Parcelable