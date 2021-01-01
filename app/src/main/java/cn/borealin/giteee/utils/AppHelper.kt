/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午1:35
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午1:35
 */

package cn.borealin.giteee.utils

import android.content.Context

object AppHelper {
    lateinit var mContext: Context

    fun init(context: Context) {
        this.mContext = context
    }
}