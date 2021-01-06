/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午5:02
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午5:02
 */

package cn.borealin.giteee.extension

inline fun <T, R> T.doWithTry(block: (T) -> R) {
    try {
        block(this)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}