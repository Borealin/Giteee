/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/3 下午7:01
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/3 下午7:01
 */

package cn.borealin.giteee.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.FragmentProfileBinding
import cn.borealin.giteee.ui.common.HomeMenuItemAdapter
import cn.borealin.giteee.ui.common.UserEventItemAdapter
import cn.borealin.giteee.utils.ToastUtils
import com.hi.dhl.jdatabinding.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable

enum class ProfileType : Serializable {
    PERSON,
    ORGANIZATION
}

@AndroidEntryPoint
class ProfileFragment : DataBindingFragment(R.layout.fragment_profile) {
    private val mBinding: FragmentProfileBinding by binding()
    private val mViewModel: ProfileViewModel by viewModels()
    private lateinit var homeMenuItemAdapter: HomeMenuItemAdapter
    private lateinit var userEventItemAdapter: UserEventItemAdapter

    private var getEventJob: Job? = null

    private fun getEvent(username: String? = null) {
        getEventJob?.cancel()
        getEventJob = lifecycleScope.launch {
            mViewModel.getEvents(username).collect {
                userEventItemAdapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMenuItemAdapter = HomeMenuItemAdapter()
        userEventItemAdapter = UserEventItemAdapter()
        mBinding.apply {
            profileViewModel = mViewModel.apply {
                getCurrentProfile().observe(viewLifecycleOwner, {
                    ToastUtils.show(requireContext(), R.string.refresh_successfully)
                })
            }
            lifecycleOwner = this@ProfileFragment
            profileRefresh.setOnRefreshListener {
                mViewModel.getCurrentProfile().observe(viewLifecycleOwner, {
                    profileRefresh.isRefreshing = false
                })
                getEvent()
            }
            profileUtilContainer.adapter = homeMenuItemAdapter
            profileEventContainer.adapter = userEventItemAdapter
            getEvent()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val KEY_PROFILE_TYPE = "key_profile_type"
        fun newInstance(profileType: ProfileType) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_PROFILE_TYPE, profileType)
            }
        }
    }
}