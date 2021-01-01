/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午1:33
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午1:33
 */

package cn.borealin.giteee.utils

import android.content.Context
import android.os.StrictMode
import androidx.startup.Initializer
import cn.borealin.giteee.BuildConfig
import timber.log.Timber

class AppInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AppHelper.init(context)
        if (!BuildConfig.DEBUG) {
            return
        }
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
        )
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        Timber.plant(Timber.DebugTree())
        return
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}