/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-21 下午2:17
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-21 下午2:16
 */

package cn.borealin.giteee.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.borealin.giteee.R
import cn.borealin.giteee.api.Status
import cn.borealin.giteee.databinding.ActivityLoginBinding
import cn.borealin.giteee.extension.finishAndStartActivity
import cn.borealin.giteee.extension.finishAndStartClearActivity
import cn.borealin.giteee.ui.home.HomeActivity
import cn.borealin.giteee.utils.ToastUtils
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val mBinding: ActivityLoginBinding by binding()
    private val mViewModel: LoginViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.login_activity_title)
        val action: String? = intent?.action
        val data: Uri? = intent?.data
        action?.let { it ->
            if (it == Intent.ACTION_VIEW) {
                data?.getQueryParameter(LoginContract.RESPONSE_TYPE)?.let { code ->
                    mViewModel.getToken(code).observe(this, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                ToastUtils.show(this, getString(R.string.login_succeed))
                                finishAndStartClearActivity(HomeActivity::class.java)
                            }
                            Status.ERROR -> {
                                ToastUtils.show(this, getString(R.string.login_failed))
                                finishAndStartActivity(LoginActivity::class.java)
                            }
                            else -> {
                            }
                        }
                    })
                }
            }
        }
        mBinding.apply {
            loginWebView.webChromeClient = WebChromeClient()
            loginWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.let {
                        if (it.scheme == view?.context?.getString(R.string.scheme_name)) {
                            finishAndStartClearActivity(Intent(Intent.ACTION_VIEW, it))
                            return true
                        }
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
            loginWebView.settings.javaScriptEnabled = true
            loginWebView.loadUrl(LoginContract.AUTH_URL)
        }
    }
}