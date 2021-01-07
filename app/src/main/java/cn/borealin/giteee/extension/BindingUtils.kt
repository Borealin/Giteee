/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午4:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午4:06
 */

package cn.borealin.giteee.extension

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.borealin.giteee.R
import cn.borealin.giteee.model.common.HomeMenuType
import cn.borealin.giteee.model.users.UserEventType
import cn.borealin.giteee.ui.common.HomeMenuItemAdapter
import coil.load
import timber.log.Timber

@BindingAdapter("srcIcon")
fun ImageView.bindIcon(icon: HomeMenuType) {
    setImageResource(icon.toDrawableIconId())
}

@BindingAdapter("srcIcon")
fun ImageView.bindIcon(icon: UserEventType) {
    setImageResource(icon.toDrawableIconId())
}

@BindingAdapter("descriptionText")
fun TextView.bindDescription(event: UserEventType) {
    val description = event.operator + " " + event.action + " " + event.target
    val span = SpannableString(description)
    span.setSpan(
        ForegroundColorSpan(Color.BLACK),
        event.operator.length,
        event.operator.length + event.action.length + 1,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    span.setSpan(
        StyleSpan(Typeface.BOLD),
        event.operator.length,
        event.operator.length + event.action.length + 1,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    text = span
}

@BindingAdapter("listData")
fun RecyclerView.bindMenuRecyclerView(list: List<HomeMenuType>?) {
    val adapter = adapter as HomeMenuItemAdapter
    adapter.submitList(list)
}

@BindingAdapter("srcImageUrl")
fun ImageView.bindImageUrl(url: String?) {
    load(url) {
        crossfade(true)
        placeholder(R.mipmap.no_portrait)
    }
}

@BindingAdapter("bindVisibleWithNullable")
fun View.bindVisibilityWithNullable(nullable: Any?) {
    visibility = if (nullable == null) View.GONE else View.VISIBLE
}

@BindingAdapter("bindVisibleWithNullableOrEmpty")
fun View.bindVisibilityWithNullableOrEmpty(nullable: Any?) {
    visibility = when (nullable) {
        null -> View.GONE
        is String -> {
            if (nullable.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        else -> View.VISIBLE
    }
}

@BindingAdapter("bindLoading")
fun bindingLoading(swipe: SwipeRefreshLayout, isLoading: Boolean) {
    Timber.tag("bindingLoading").e(" isLoading = $isLoading")
    swipe.isRefreshing = isLoading
    if (!isLoading) swipe.isEnabled = false
}
