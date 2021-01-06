/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:16
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:16
 */

package cn.borealin.giteee.ui.common

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import cn.borealin.giteee.R

sealed class UserEventType(
    val operator: String,
    val action: String,
    val target: String,
    val avatarUrl: String?,
    val time: String?
) {
    class Push(operator: String, target: String, avatarUrl: String?, time: String?) :
        UserEventType(operator, "pushed", target, avatarUrl, time)

    class Create(operator: String, target: String, avatarUrl: String?, time: String?) :
        UserEventType(operator, "created", target, avatarUrl, time)

    class Member(operator: String, target: String, avatarUrl: String?, time: String?) :
        UserEventType(operator, "joined", target, avatarUrl, time)

    class Undefined(operator: String, avatarUrl: String? = null, time: String? = null) :
        UserEventType(operator, "do something", "", avatarUrl, time)

    @DrawableRes
    fun toDrawableIconId(): Int {
        return when (this) {
            is Push -> R.drawable.ic_pr_gray_24dp
            is Create -> R.drawable.ic_gists_gray_24dp
            is Member -> R.drawable.ic_organization_gray_24dp
            is Undefined -> R.drawable.ic_unknown_gray_24dp
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEventType> =
            object : DiffUtil.ItemCallback<UserEventType>() {
                override fun areItemsTheSame(oldItem: UserEventType, newItem: UserEventType) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: UserEventType, newItem: UserEventType) =
                    oldItem.operator == newItem.operator
                            && oldItem.action == newItem.action
                            && oldItem.target == newItem.target
                            && oldItem.time == newItem.time
            }
    }
}