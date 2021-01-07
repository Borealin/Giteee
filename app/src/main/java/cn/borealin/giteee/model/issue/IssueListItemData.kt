/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:13
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:13
 */

package cn.borealin.giteee.model.issue

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

typealias IssueListItemCallback = (IssueListItemData) -> Unit

@Parcelize
data class IssueListItemData(
    val repoName: String,
    val issueNumber: String,
    val title: String,
    val timeRemain: String
) : Parcelable {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<IssueListItemData> =
            object : DiffUtil.ItemCallback<IssueListItemData>() {
                override fun areItemsTheSame(
                    oldItem: IssueListItemData,
                    newItem: IssueListItemData
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: IssueListItemData,
                    newItem: IssueListItemData
                ) =
                    oldItem.repoName == newItem.repoName && oldItem.issueNumber == newItem.issueNumber
            }
    }
}

interface IssueListConverter {
    fun toIssueListItemData(): IssueListItemData
}