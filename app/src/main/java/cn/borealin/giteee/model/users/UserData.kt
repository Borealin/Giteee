/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 上午10:13
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 上午10:13
 */

package cn.borealin.giteee.model.users

import android.os.Parcelable
import cn.borealin.giteee.model.common.HomeMenuType
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val name: String,
    val avatar: String?,
    val loginName: String,
    val biography: String?,
    val blog: String?,
    val email: String?,
    val followerCount: Int?,
    val followingCount: Int?,
    val publicRepos: Int,
    val publicOrgs: Int,
    val publicGists: Int,
    val stared: Int,
    val watched: Int
) : Parcelable {
    fun toProfileDetail(): ProfileDetail {
        return ProfileDetail(
            name = name,
            avatar = avatar,
            loginName = loginName,
            biography = biography,
            blog = blog,
            email = email,
            followerCount = followerCount,
            followingCount = followingCount
        )
    }

    fun toHomeMenuList(): List<HomeMenuType> {
        return listOf(
            HomeMenuType.Repository(publicRepos),
            HomeMenuType.Organization(publicOrgs),
            HomeMenuType.Gists(publicGists),
            HomeMenuType.Star(stared),
            HomeMenuType.Watch(watched)
        )
    }

    companion object {
        fun fromRawUserData(userData: RawUserData, orgsCount: Int): UserData {
            return UserData(
                name = userData.name,
                avatar = userData.avatarUrl,
                loginName = userData.login,
                biography = userData.bio,
                blog = userData.blog,
                email = userData.email,
                followerCount = userData.followers,
                followingCount = userData.following,
                publicRepos = userData.publicRepos,
                publicOrgs = orgsCount,
                publicGists = userData.publicGists,
                stared = userData.stared,
                watched = userData.watched
            )
        }

        fun fromAccountName(accountName: String, accountLoginName: String = ""): UserData {
            return UserData(
                name = accountName,
                avatar = null,
                loginName = accountLoginName,
                biography = null,
                blog = null,
                email = null,
                followerCount = null,
                followingCount = null,
                publicRepos = -1,
                publicOrgs = -1,
                publicGists = -1,
                stared = -1,
                watched = -1
            )
        }
    }
}