/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 下午3:25
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 下午3:25
 */

package cn.borealin.giteee.model.users

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileDetail(
    val name: String,
    val avatar: String?,
    val loginName: String?,
    val biography: String?,
    val blog: String?,
    val email: String?,
    val followerCount: Int?,
    val followingCount: Int?
) : Parcelable