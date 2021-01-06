/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午5:13
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午5:13
 */

package cn.borealin.giteee.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.borealin.giteee.databinding.HomeMenuItemBinding

class HomeMenuItemAdapter :
    ListAdapter<HomeMenuType, HomeMenuItemHolder>(HomeMenuType.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMenuItemHolder {
        return HomeMenuItemHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeMenuItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class HomeMenuItemHolder(private val binding: HomeMenuItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(type: HomeMenuType) {
        binding.apply {
            item = type
            clickListener = View.OnClickListener {
                when (type) {
                    is HomeMenuType.Issue -> {
                    }
                    is HomeMenuType.PullRequest -> {
                    }
                    is HomeMenuType.Repository -> {
                    }
                    is HomeMenuType.Organization -> {
                    }
                    is HomeMenuType.Star -> {
                    }
                    is HomeMenuType.Watch -> {
                    }
                    is HomeMenuType.Gists -> {
                    }
                }
            }
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): HomeMenuItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HomeMenuItemBinding.inflate(layoutInflater, parent, false)
            return HomeMenuItemHolder(binding)
        }
    }
}