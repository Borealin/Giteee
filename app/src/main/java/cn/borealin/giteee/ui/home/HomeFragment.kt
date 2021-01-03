/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午2:14
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午1:01
 */

package cn.borealin.giteee.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.FragmentHomeBinding
import cn.borealin.giteee.ui.common.HomeMenuItemAdapter
import com.hi.dhl.jdatabinding.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : DataBindingFragment(R.layout.fragment_home) {

    private val mBinding: FragmentHomeBinding by binding()
    private val mViewModel: HomeViewModel by viewModels()
    private lateinit var homeMenuItemAdapter: HomeMenuItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMenuItemAdapter = HomeMenuItemAdapter()
        mBinding.apply {
            homeViewModel = mViewModel
            lifecycleOwner = this@HomeFragment
            myWorkContainer.adapter = homeMenuItemAdapter
        }
    }
}