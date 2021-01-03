/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-21 下午7:52
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-21 下午7:51
 */

package cn.borealin.giteee.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.ActivityMainBinding
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by binding()
    private val mViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            homeViewModel = mViewModel
            lifecycleOwner = this@MainActivity
            val navController = findNavController(R.id.nav_host_fragment_activity_navigation)
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_notifications,
                    R.id.navigation_profile
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}