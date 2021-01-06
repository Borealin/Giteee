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
import cn.borealin.giteee.model.common.HomeMenuType
import cn.borealin.giteee.model.common.HomeMenuTypeCallback


class HomeMenuItemAdapter(private val homeMenuTypeCallback: HomeMenuTypeCallback) :
    ListAdapter<HomeMenuType, HomeMenuItemHolder>(HomeMenuType.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMenuItemHolder {
        return HomeMenuItemHolder.from(parent, homeMenuTypeCallback)
    }

    override fun onBindViewHolder(holder: HomeMenuItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class HomeMenuItemHolder(
    private val binding: HomeMenuItemBinding,
    private val homeMenuTypeCallback: HomeMenuTypeCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(type: HomeMenuType) {
        binding.apply {
            item = type
            clickListener = View.OnClickListener {
                homeMenuTypeCallback(type)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            homeMenuTypeCallback: HomeMenuTypeCallback
        ): HomeMenuItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HomeMenuItemBinding.inflate(layoutInflater, parent, false)
            return HomeMenuItemHolder(binding, homeMenuTypeCallback)
        }
    }
}