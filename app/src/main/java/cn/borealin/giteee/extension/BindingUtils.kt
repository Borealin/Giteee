/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午4:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午4:06
 */

package cn.borealin.giteee.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.borealin.giteee.ui.common.HomeMenuItemAdapter
import cn.borealin.giteee.ui.common.HomeMenuType

@BindingAdapter("srcIcon")
fun ImageView.bindIcon(icon: HomeMenuType) {
    setImageResource(icon.toDrawableIconId())
}

@BindingAdapter("listData")
fun RecyclerView.bindMenuRecyclerView(list: List<HomeMenuType>?) {
    val adapter = adapter as HomeMenuItemAdapter
    adapter.submitList(list)
}