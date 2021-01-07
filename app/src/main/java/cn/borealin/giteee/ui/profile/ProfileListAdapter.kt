/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午12:11
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午12:11
 */

package cn.borealin.giteee.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.borealin.giteee.databinding.ProfileListItemBinding
import cn.borealin.giteee.extension.doWithTry
import cn.borealin.giteee.model.users.ProfileListItemCallback
import cn.borealin.giteee.model.users.ProfileListItemData

class ProfileListAdapter(private val onClickListener: ProfileListItemCallback) :
    PagingDataAdapter<ProfileListItemData, ProfileListItemHolder>(ProfileListItemData.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileListItemHolder {
        return ProfileListItemHolder.from(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: ProfileListItemHolder, position: Int) {
        doWithTry {
            val item = getItem(position)
            item?.let {
                holder.bind(it)
            }
        }
    }

}

class ProfileListItemHolder(
    private val binding: ProfileListItemBinding,
    private val onClickListener: ProfileListItemCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(type: ProfileListItemData) {
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
            onClickListener: ProfileListItemCallback
        ): ProfileListItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProfileListItemBinding.inflate(layoutInflater, parent, false)
            return ProfileListItemHolder(binding, onClickListener)
        }
    }
}