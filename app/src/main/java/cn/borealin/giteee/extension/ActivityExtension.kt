/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/1 上午12:38
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/1 上午12:38
 */

package cn.borealin.giteee.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

fun <T : Context> Activity.startNewActivity(tClass: KClass<T>) {
    tClass.objectInstance?.let {
        finish()
        overridePendingTransition(0, 0)
        startActivity(it.newIntent(this))
        overridePendingTransition(0, 0)
    }
}

fun Activity.startNewActivity(intent: Intent) {
    finish()
    overridePendingTransition(0, 0)
    startActivity(intent)
    overridePendingTransition(0, 0)
}

fun <T : Context> Activity.startClearActivity(tClass: KClass<T>) {
    tClass.objectInstance?.let {
        finish()
        overridePendingTransition(0, 0)
        startActivity(it.newClearIntent(this))
        overridePendingTransition(0, 0)
    }
}

fun Activity.startClearActivity(intent: Intent) {
    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
    finish()
    overridePendingTransition(0, 0)
    startActivity(intent)
    overridePendingTransition(0, 0)
}