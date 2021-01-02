/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/1 上午12:45
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/1 上午12:45
 */

package cn.borealin.giteee.extension

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isConnectedNetwork(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val allNetworks = cm.allNetworks
    for (network in allNetworks) {
        val networkCapabilities = cm.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            )
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                ) return true
        }
    }
    return false
}

fun <T : Context> Context.newIntent(javaClass: Class<T>): Intent {
    return Intent(this, javaClass)
}

fun <T : Context> Context.newClearIntent(javaClass: Class<T>): Intent {
    val intent = this.newIntent(javaClass)
    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
    return intent
}
