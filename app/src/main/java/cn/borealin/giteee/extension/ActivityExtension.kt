/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/1 上午12:38
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/1 上午12:38
 */

package cn.borealin.giteee.extension

import android.app.Activity
import android.content.Context
import android.content.Intent

fun <T : Context> Activity.finishAndStartActivity(javaClass: Class<T>) {
    finish()
    overridePendingTransition(0, 0)
    startActivity(newIntent(javaClass))
    overridePendingTransition(0, 0)

}

fun Activity.finishAndStartActivity(intent: Intent) {
    finish()
    overridePendingTransition(0, 0)
    startActivity(intent)
    overridePendingTransition(0, 0)
}

fun <T : Context> Activity.finishAndStartClearActivity(javaClass: Class<T>) {
    finish()
    overridePendingTransition(0, 0)
    startActivity(newClearIntent(javaClass))
    overridePendingTransition(0, 0)
}

fun Activity.finishAndStartClearActivity(intent: Intent) {
    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
    finish()
    overridePendingTransition(0, 0)
    startActivity(intent)
    overridePendingTransition(0, 0)
}