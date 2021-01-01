/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午1:55
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午1:55
 */

package cn.borealin.giteee.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ToastUtils {
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var mToast: Toast

    fun show(
        context: Context,
        @StringRes resId: Int,
        time: Int = Toast.LENGTH_SHORT
    ) {
        show(context, context.getString(resId), time)
    }

    fun show(
        context: Context?,
        content: String?,
        time: Int = Toast.LENGTH_SHORT
    ) {
        scope.launch {
            if (::mToast.isInitialized) {
                mToast.cancel()
                mToast = Toast.makeText(context, content, time)
                mToast.show()
            } else {
                mToast = Toast.makeText(context, content, time)
                mToast.show()
            }
        }
    }
}