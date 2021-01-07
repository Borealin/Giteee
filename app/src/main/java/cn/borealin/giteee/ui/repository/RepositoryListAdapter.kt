/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午9:41
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午9:41
 */

package cn.borealin.giteee.ui.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.borealin.giteee.databinding.RepositoryListItemBinding
import cn.borealin.giteee.extension.doWithTry
import cn.borealin.giteee.model.common.RepositoryListItemCallback
import cn.borealin.giteee.model.common.RepositoryListItemData

class RepositoryListAdapter(private val onClickListener: RepositoryListItemCallback) :
    PagingDataAdapter<RepositoryListItemData, RepositoryListItemHolder>(RepositoryListItemData.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListItemHolder {
        return RepositoryListItemHolder.from(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: RepositoryListItemHolder, position: Int) {
        doWithTry {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }
    }
}

class RepositoryListItemHolder(
    private val binding: RepositoryListItemBinding,
    private val onClickListener: RepositoryListItemCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(type: RepositoryListItemData) {
        binding.apply {
            item = type
            clickListener = View.OnClickListener {
                onClickListener(type)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickListener: RepositoryListItemCallback
        ): RepositoryListItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RepositoryListItemBinding.inflate(layoutInflater, parent, false)
            return RepositoryListItemHolder(binding, onClickListener)
        }
    }
}