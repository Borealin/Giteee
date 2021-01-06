/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午4:56
 */

package cn.borealin.giteee.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.borealin.giteee.databinding.UserEventItemBinding
import cn.borealin.giteee.extension.doWithTry
import cn.borealin.giteee.model.common.UserEventType

class UserEventItemAdapter :
    PagingDataAdapter<UserEventType, UserEventItemHolder>(UserEventType.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserEventItemHolder {
        return UserEventItemHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserEventItemHolder, position: Int) {
        doWithTry {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }
    }

}

class UserEventItemHolder(private val binding: UserEventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(type: UserEventType) {
        binding.apply {
            item = type
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): UserEventItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = UserEventItemBinding.inflate(layoutInflater, parent, false)
            return UserEventItemHolder(binding)
        }
    }
}