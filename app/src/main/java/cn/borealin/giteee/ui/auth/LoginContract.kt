/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-22 下午2:11
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-22 下午2:11
 */

package cn.borealin.giteee.ui.auth

object LoginContract {
    const val CLIENT_ID = "2e5e0382815505c8a610eeabb1c26948ba133c2b9255538a9c0414882d0c2cad"
    const val CLIENT_SECRET = "1a3c7c5542ba5427815f958e50f850916bedf60a6c08aa3cfae6268a11a75d27"
    const val CALLBACK = "cn.borealin.giteee://oauth2"
    const val RESPONSE_TYPE = "code"
    const val AUTH_URL =
        "https://gitee.com/oauth/authorize?client_id=$CLIENT_ID&redirect_uri=$CALLBACK&response_type=$RESPONSE_TYPE"

}