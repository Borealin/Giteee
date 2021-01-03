/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午5:13
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午5:13
 */

package cn.borealin.giteee.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.borealin.giteee.databinding.HomeMenuItemBinding

class HomeMenuItemAdapter :
    ListAdapter<HomeMenuType, HomeMenuItemHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMenuItemHolder {
        return HomeMenuItemHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeMenuItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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

class HomeMenuItemHolder(private val binding: HomeMenuItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.setClickListener {
            binding.item?.let {

            }
        }
    }

    fun bind(type: HomeMenuType) {
        binding.apply {
            item = type
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