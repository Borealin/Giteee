/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-21 下午4:14
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-21 下午3:03
 */

package cn.borealin.giteee.ui.entrance

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.extension.finishAndStartClearActivity
import cn.borealin.giteee.ui.auth.LoginActivity
import cn.borealin.giteee.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EntranceActivity : AppCompatActivity() {
    private val mViewModel: EntranceViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        setVisible(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            mViewModel.apply {
                requireLogin.observe(this@EntranceActivity, {
                    if (it == true) {
                        finishAndStartClearActivity(LoginActivity::class.java)
                    } else {
                        finishAndStartClearActivity(MainActivity::class.java)
                    }
                })
            }
        }
    }
}