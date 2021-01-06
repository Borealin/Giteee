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
import cn.borealin.giteee.api.doFailure
import cn.borealin.giteee.api.doSuccess
import cn.borealin.giteee.databinding.FragmentProfileBinding
import cn.borealin.giteee.model.common.HomeMenuType
import cn.borealin.giteee.model.common.HomeMenuTypeCallback
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
    private var userLoginName: String? = null

    private val itemOnClickListener: HomeMenuTypeCallback = {
        when (it) {
            is HomeMenuType.Issue -> TODO()
            is HomeMenuType.PullRequest -> TODO()
            is HomeMenuType.Repository -> TODO()
            is HomeMenuType.Organization -> {
                userLoginName?.let { name ->
                    startActivity(
                        ProfileListActivity.newIntent(
                            requireContext(),
                            ProfileListType.Organization(name)
                        )
                    )
                }
            }
            is HomeMenuType.Gists -> TODO()
            is HomeMenuType.Star -> TODO()
            is HomeMenuType.Watch -> TODO()
        }
    }

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
        homeMenuItemAdapter = HomeMenuItemAdapter(itemOnClickListener)
        userEventItemAdapter = UserEventItemAdapter()
        val argName = arguments?.getString(KEY_USERNAME)
        if (argName == null) {
            mViewModel.getCurrentProfile().observe(viewLifecycleOwner, {
                it.doSuccess { data ->
                    userLoginName = data.loginName
                }
                it.doFailure {
                    ToastUtils.show(requireContext(), R.string.get_profile_failed)
                }
            })
        } else {
            userLoginName = argName
        }
        mBinding.apply {
            profileViewModel = mViewModel
            lifecycleOwner = this@ProfileFragment
            profileRefresh.setOnRefreshListener {
                mViewModel.getCurrentProfile().observe(viewLifecycleOwner, {
                    profileRefresh.isRefreshing = false
                })
                getEvent(userLoginName)
            }
            followerContainer.setOnClickListener {
                userLoginName?.let {
                    startActivity(
                        ProfileListActivity.newIntent(
                            requireContext(),
                            ProfileListType.Follower(it)
                        )
                    )
                }
            }
            followingContainer.setOnClickListener {
                userLoginName?.let {
                    startActivity(
                        ProfileListActivity.newIntent(
                            requireContext(),
                            ProfileListType.Following(it)
                        )
                    )
                }
            }
            profileUtilContainer.adapter = homeMenuItemAdapter
            profileEventContainer.adapter = userEventItemAdapter
            getEvent(userLoginName)
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
        private const val KEY_USERNAME = "key_username"
        fun newInstance(profileType: ProfileType, username: String) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_PROFILE_TYPE, profileType)
                putString(KEY_USERNAME, username)
            }
        }
    }
}