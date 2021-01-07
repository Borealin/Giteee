/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:35
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:35
 */

package cn.borealin.giteee.ui.issue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.borealin.giteee.databinding.IssueListItemBinding
import cn.borealin.giteee.extension.doWithTry
import cn.borealin.giteee.model.issue.IssueListItemCallback
import cn.borealin.giteee.model.issue.IssueListItemData

class IssueListAdapter(private val onClickListener: IssueListItemCallback) :
    PagingDataAdapter<IssueListItemData, IssueListItemHolder>(IssueListItemData.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueListItemHolder {
        return IssueListItemHolder.from(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: IssueListItemHolder, position: Int) {
        doWithTry {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }
    }
}

class IssueListItemHolder(
    private val binding: IssueListItemBinding,
    private val onClickListener: IssueListItemCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(type: IssueListItemData) {
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
            onClickListener: IssueListItemCallback
        ): IssueListItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = IssueListItemBinding.inflate(layoutInflater, parent, false)
            return IssueListItemHolder(binding, onClickListener)
        }
    }
}
