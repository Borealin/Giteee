/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-21 下午7:52
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-21 下午7:51
 */

package cn.borealin.giteee.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.borealin.giteee.databinding.ActivityHomeBinding
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val mBinding: ActivityHomeBinding by binding()
    private val mViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.apply {
            homeViewModel = mViewModel
            lifecycleOwner = this@HomeActivity
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}