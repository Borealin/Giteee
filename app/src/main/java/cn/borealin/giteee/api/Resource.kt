/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-16 上午10:54
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-16 上午10:54
 */

package cn.borealin.giteee.api

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(val throwable: Throwable?) : Resource<Nothing>()
}

inline fun <reified T> Resource<T>.doSuccess(success: (T) -> Unit) {
    if (this is Resource.Success) {
        success(value)
    }
}

inline fun <reified T> Resource<T>.doFailure(failure: (Throwable?) -> Unit) {
    if (this is Resource.Failure) {
        failure(throwable)
    }
}

//
//data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
//    companion object {
//        fun <T> success(data: T): Resource<T> =
//            Resource(status = Status.SUCCESS, data = data, message = null)
//
//        fun <T> error(message: String?, data: T? = null): Resource<T> =
//            Resource(status = Status.ERROR, data = data, message = message)
//
//    }
//}
//
//enum class Status {
//    SUCCESS,
//    ERROR
//}