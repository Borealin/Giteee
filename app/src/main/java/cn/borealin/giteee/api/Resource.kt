/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-16 上午10:54
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-16 上午10:54
 */

package cn.borealin.giteee.api


data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(message: String?, data: T? = null): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T? = null): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}