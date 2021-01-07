/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 上午10:13
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 上午10:13
 */

package cn.borealin.giteee.model.users

import android.os.Parcelable
import cn.borealin.giteee.model.common.HomeMenuType
import cn.borealin.giteee.model.organization.RawOrgsData
import cn.borealin.giteee.ui.profile.ProfileType
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
    val watched: Int,
    val member: Int
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

    fun toHomeMenuList(profileType: ProfileType): List<HomeMenuType> {
        return when (profileType) {
            is ProfileType.User -> listOf(
                HomeMenuType.Repository(publicRepos),
                HomeMenuType.Organization(publicOrgs),
                HomeMenuType.Star(stared),
                HomeMenuType.Watch(watched)
            )
            is ProfileType.My -> listOf(
                HomeMenuType.Repository(publicRepos),
                HomeMenuType.Organization(publicOrgs),
                HomeMenuType.Gists(publicGists),
                HomeMenuType.Star(stared),
                HomeMenuType.Watch(watched)
            )
            is ProfileType.Organization -> listOf(
                HomeMenuType.Repository(publicRepos),
                HomeMenuType.Member(member)
            )
        }
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
                watched = userData.watched,
                member = -1
            )
        }

        fun fromRawUserData(userData: RawUserData, orgsCount: Int, repoCount: Int): UserData {
            return UserData(
                name = userData.name,
                avatar = userData.avatarUrl,
                loginName = userData.login,
                biography = userData.bio,
                blog = userData.blog,
                email = userData.email,
                followerCount = userData.followers,
                followingCount = userData.following,
                publicRepos = repoCount,
                publicOrgs = orgsCount,
                publicGists = userData.publicGists,
                stared = userData.stared,
                watched = userData.watched,
                member = -1
            )
        }

        fun fromRawOrgsData(userData: RawOrgsData): UserData {
            return UserData(
                name = userData.name,
                avatar = userData.avatarUrl,
                loginName = userData.login,
                biography = userData.description,
                blog = null,
                email = userData.email,
                followerCount = userData.followCount,
                followingCount = null,
                publicRepos = userData.publicRepos,
                publicOrgs = -1,
                publicGists = -1,
                stared = -1,
                watched = -1,
                member = userData.members
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
                watched = -1,
                member = -1
            )
        }
    }
}