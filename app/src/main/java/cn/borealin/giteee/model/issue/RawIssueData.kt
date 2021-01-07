/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:18
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:18
 */

package cn.borealin.giteee.model.issue

import android.os.Parcelable
import cn.borealin.giteee.utils.TimeUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawIssueData(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("number")
    val number: String,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("repository")
    val repository: RawIssueRepoData,
    @Expose
    @SerializedName("created_at")
    val createAt: String
) : Parcelable, IssueListConverter {
    override fun toIssueListItemData(): IssueListItemData {
        return IssueListItemData(
            repoName = repository.humanName,
            issueNumber = number,
            title = title,
            timeRemain = TimeUtils.fromTimeToRemaining(createAt)
        )
    }
}

@Parcelize
data class RawIssueRepoData(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("full_name")
    val fullName: String,
    @Expose
    @SerializedName("human_name")
    val humanName: String,
) : Parcelable