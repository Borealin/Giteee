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
import android.net.NetworkInfo
import android.os.Build

fun Context.isConnectedNetwork(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    } else {
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
}

fun Context.newIntent(context: Context): Intent {
    return Intent(context, this::class.java)
}

fun Context.newClearIntent(context: Context): Intent {
    val intent = newIntent(context)
    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
    return intent
}
