/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午1:04
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午1:04
 */

package cn.borealin.giteee.ui.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.BuildConfig
import cn.borealin.giteee.databinding.ActivityAboutBinding
import cn.borealin.giteee.extension.finishAndStartClearActivity
import cn.borealin.giteee.ui.entrance.EntranceActivity
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {
    private val mBinding: ActivityAboutBinding by binding()
    private val mViewModel: SettingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            settingViewModel = mViewModel
            lifecycleOwner = this@AboutActivity
            appVersion.text = BuildConfig.VERSION_NAME
            logoutButton.setOnClickListener {
                lifecycleScope.launch {
                    mViewModel.logout()
                    finishAndStartClearActivity(EntranceActivity::class.java)
                }
            }
            toolbarAbout.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }
}