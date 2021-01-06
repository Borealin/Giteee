/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午5:07
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午5:07
 */

package cn.borealin.giteee.ui.common

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import cn.borealin.giteee.R
import java.io.Serializable

sealed class HomeMenuType(val title: String, val count: Int) : Serializable {
    class Issue(count: Int) : HomeMenuType("Issues", count)
    class PullRequest(count: Int) : HomeMenuType("Pull Requests", count)
    class Repository(count: Int) : HomeMenuType("Repositories", count)
    class Organization(count: Int) : HomeMenuType("Organizations", count)
    class Gists(count: Int) : HomeMenuType("Gists", count)
    class Star(count: Int) : HomeMenuType("Starred", count)
    class Watch(count: Int) : HomeMenuType("Watched", count)

    @DrawableRes
    fun toDrawableIconId(): Int {
        return when (this) {
            is Issue -> R.drawable.ic_issue_green_24dp
            is PullRequest -> R.drawable.ic_pr_blue_24dp
            is Repository -> R.drawable.ic_repo_purple_24dp
            is Organization -> R.drawable.ic_organization_orange_24dp
            is Star -> R.drawable.ic_star_yellow_24dp
            is Watch -> R.drawable.ic_watch_teal_24dp
            is Gists -> R.drawable.ic_gists_blue_24dp
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HomeMenuType> =
            object : DiffUtil.ItemCallback<HomeMenuType>() {
                override fun areItemsTheSame(oldItem: HomeMenuType, newItem: HomeMenuType) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: HomeMenuType, newItem: HomeMenuType) =
                    oldItem.title == newItem.title
            }
    }
}