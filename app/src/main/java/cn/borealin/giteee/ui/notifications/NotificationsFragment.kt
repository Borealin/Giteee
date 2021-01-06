/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/4 上午1:01
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/4 上午1:01
 */

package cn.borealin.giteee.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.FragmentNotificationsBinding
import cn.borealin.giteee.ui.common.UserEventItemAdapter
import com.hi.dhl.jdatabinding.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NotificationsFragment : DataBindingFragment(R.layout.fragment_notifications) {

    private val mBinding: FragmentNotificationsBinding by binding()
    private val mViewModel: NotificationsViewModel by viewModels()

    private lateinit var userEventItemAdapter: UserEventItemAdapter

    private var getEventJob: Job? = null

    private fun getEvent(username: String? = null) {
        getEventJob?.cancel()
        getEventJob = lifecycleScope.launch {
            mViewModel.getEvents(username).onCompletion {
                mBinding.eventRefresh.isRefreshing = false
            }.collect {
                userEventItemAdapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userEventItemAdapter = UserEventItemAdapter()
        mBinding.apply {
            notificationsViewModel = mViewModel
            lifecycleOwner = this@NotificationsFragment
            eventRefresh.setOnRefreshListener {
                getEvent()
            }
            notificationContainer.adapter = userEventItemAdapter
            getEvent()
        }
    }
}