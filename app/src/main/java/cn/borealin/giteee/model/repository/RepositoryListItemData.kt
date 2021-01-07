/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午11:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午9:50
 */

package cn.borealin.giteee.model.repository

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

typealias RepositoryListItemCallback = (RepositoryListItemData) -> Unit

@Parcelize
data class RepositoryListItemData(
    val name: String,
    val login: String,
    val stargazer: Int,
    val description: String?,
    val language: String?
) : Parcelable {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<RepositoryListItemData> =
            object : DiffUtil.ItemCallback<RepositoryListItemData>() {
                override fun areItemsTheSame(
                    oldItem: RepositoryListItemData,
                    newItem: RepositoryListItemData
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: RepositoryListItemData,
                    newItem: RepositoryListItemData
                ) = oldItem.login == newItem.login
            }
    }
}

interface RepositoryListConverter {
    fun toRepositoryListItemData(): RepositoryListItemData
}