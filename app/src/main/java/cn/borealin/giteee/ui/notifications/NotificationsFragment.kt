/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午1:01
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午1:01
 */

package cn.borealin.giteee.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.FragmentNotificationsBinding
import com.hi.dhl.jdatabinding.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : DataBindingFragment(R.layout.fragment_notifications) {

    private val mBinding: FragmentNotificationsBinding by binding()
    private val mViewModel: NotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            notificationsViewModel = mViewModel
            lifecycleOwner = this@NotificationsFragment
        }
    }
}