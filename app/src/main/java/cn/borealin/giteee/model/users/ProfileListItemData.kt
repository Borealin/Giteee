/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午11:55
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午2:12
 */

package cn.borealin.giteee.model.users

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

typealias ProfileListItemCallback = (ProfileListItemData) -> Unit

@Parcelize
data class ProfileListItemData(
    val name: String,
    val login: String,
    val avatarUrl: String
) : Parcelable {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ProfileListItemData> =
            object : DiffUtil.ItemCallback<ProfileListItemData>() {
                override fun areItemsTheSame(
                    oldItem: ProfileListItemData,
                    newItem: ProfileListItemData
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: ProfileListItemData,
                    newItem: ProfileListItemData
                ) = oldItem.login == newItem.login
            }
    }
}

interface ProfileListConverter {
    fun toProfileListItemData(): ProfileListItemData
}