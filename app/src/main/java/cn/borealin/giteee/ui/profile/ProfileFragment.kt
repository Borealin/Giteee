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
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.FragmentProfileBinding
import cn.borealin.giteee.model.common.HomeMenuType
import cn.borealin.giteee.model.common.HomeMenuTypeCallback
import cn.borealin.giteee.ui.common.HomeMenuItemAdapter
import cn.borealin.giteee.ui.common.UserEventItemAdapter
import com.hi.dhl.jdatabinding.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : DataBindingFragment(R.layout.fragment_profile) {
    private val mBinding: FragmentProfileBinding by binding()
    private val mViewModel: ProfileViewModel by viewModels()
    private lateinit var homeMenuItemAdapter: HomeMenuItemAdapter
    private lateinit var userEventItemAdapter: UserEventItemAdapter
    private var profileType: ProfileType? = null

    private val itemOnClickListener: HomeMenuTypeCallback = {
        when (it) {
            is HomeMenuType.Organization -> {
                profileType?.let { profile ->
                    startActivity(
                        ProfileListActivity.newIntent(
                            requireContext(),
                            ProfileListType.Organization(profile.username)
                        )
                    )
                }
            }
            is HomeMenuType.Member -> {
                profileType?.let { profile ->
                    startActivity(
                        ProfileListActivity.newIntent(
                            requireContext(),
                            ProfileListType.Member(profile.username)
                        )
                    )
                }
            }
            else -> {

            }
        }
    }

    private var getEventJob: Job? = null
    private fun getEvent(username: String? = null) {
        getEventJob?.cancel()
        getEventJob = lifecycleScope.launch {
            profileType?.let {
                when {
                    arguments?.getParcelable<ProfileType>(KEY_PROFILE_TYPE) != null -> {
                        mViewModel.getPublicEvents(it.username)
                    }
                    it is ProfileType.User -> {
                        mViewModel.getEvents(it.username)
                    }
                    else -> {
                        mViewModel.getOrganizationEvents(it.username)
                    }
                }.collect { data ->
                    userEventItemAdapter.submitData(data)
                }
            } ?: run {
                mViewModel.getEvents(username).collect {
                    userEventItemAdapter.submitData(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMenuItemAdapter = HomeMenuItemAdapter(itemOnClickListener)
        userEventItemAdapter = UserEventItemAdapter()
        val argName = arguments?.getParcelable<ProfileType>(KEY_PROFILE_TYPE)
        if (argName == null) {
            mViewModel.localName.observe(viewLifecycleOwner, {
                profileType = ProfileType.User(it)
                getEvent(it)
            })
        } else {
            profileType = argName
        }
        mBinding.apply {
            profileViewModel = mViewModel
            lifecycleOwner = this@ProfileFragment
            if (arguments?.getParcelable<ProfileType>(KEY_PROFILE_TYPE) != null) {
                toolbarProfile.menu.removeItem(R.id.action_settings)
            }
            profileRefresh.setOnRefreshListener {
                mViewModel.getUserProfile(profileType).observe(viewLifecycleOwner, {
                    profileRefresh.isRefreshing = false
                })
                profileType?.let {
                    getEvent(profileType?.username)
                }
            }
            followerContainer.setOnClickListener {
                profileType?.username?.let {
                    startActivity(
                        ProfileListActivity.newIntent(
                            requireContext(),
                            ProfileListType.Follower(it)
                        )
                    )
                }
            }
            followingContainer.setOnClickListener {
                profileType?.username?.let {
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
            mViewModel.getUserProfile(profileType).observe(viewLifecycleOwner, {
                profileRefresh.isRefreshing = false
                profileType?.let {
                    getEvent(profileType?.username)
                }
            })
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

        fun addFragment(
            manager: FragmentManager,
            profileType: ProfileType,
            fragmentContainerId: Int
        ) {

            manager.commit {
                val bundle = bundleOf(KEY_PROFILE_TYPE to profileType)
                replace(fragmentContainerId, ProfileFragment::class.java, bundle)
            }
        }
    }
}