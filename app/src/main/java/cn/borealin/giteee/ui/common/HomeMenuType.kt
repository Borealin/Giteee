/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午5:07
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午5:07
 */

package cn.borealin.giteee.ui.common

import androidx.annotation.DrawableRes
import cn.borealin.giteee.R
import java.io.Serializable

enum class HomeMenuType(val title: String, val count: String) : Serializable {
    ISSUE("Issues", ""),
    PULL_REQUEST("Pull Requests", ""),
    REPOSITORY("Repositories", ""),
    ORGANIZATION("Organizations", ""),
    STAR("Starred", "");

    @DrawableRes
    fun toDrawableIconId(): Int {
        return when (this) {
            ISSUE -> R.drawable.ic_issue_green_24dp
            PULL_REQUEST -> R.drawable.ic_pr_blue_24dp
            REPOSITORY -> R.drawable.ic_repo_purple_24dp
            ORGANIZATION -> R.drawable.ic_organization_orange_24dp
            STAR -> R.drawable.ic_star_yellow_24dp
        }
    }

}
