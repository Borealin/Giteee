/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/3 下午7:01
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/3 下午7:01
 */

package cn.borealin.giteee.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.FragmentProfileBinding
import cn.borealin.giteee.extension.newIntent
import cn.borealin.giteee.model.common.HomeMenuType
import cn.borealin.giteee.model.common.HomeMenuTypeCallback
import cn.borealin.giteee.ui.common.HomeMenuItemAdapter
import cn.borealin.giteee.ui.common.UserEventItemAdapter
import cn.borealin.giteee.ui.repository.RepositoryListActivity
import cn.borealin.giteee.ui.repository.RepositoryListType
import cn.borealin.giteee.ui.setting.AboutActivity
import cn.borealin.giteee.utils.ToastUtils
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
            is HomeMenuType.Repository -> {
                profileType?.let { profile ->
                    when (profile) {
                        is ProfileType.User -> {
                            startActivity(
                                RepositoryListActivity.newIntent(
                                    requireContext(),
                                    RepositoryListType.PublicRepository(profile.username)
                                )
                            )
                        }
                        is ProfileType.My -> {
                            startActivity(
                                RepositoryListActivity.newIntent(
                                    requireContext(),
                                    RepositoryListType.MyRepository()
                                )
                            )
                        }
                        is ProfileType.Organization -> {
                            startActivity(
                                RepositoryListActivity.newIntent(
                                    requireContext(),
                                    RepositoryListType.OrganizationRepository(profile.username)
                                )
                            )
                        }
                    }
                }
            }
            is HomeMenuType.Watch -> {
                profileType?.let { profile ->
                    startActivity(
                        RepositoryListActivity.newIntent(
                            requireContext(),
                            RepositoryListType.Watch(profile.username)
                        )
                    )
                }
            }
            is HomeMenuType.Star -> {
                profileType?.let { profile ->
                    startActivity(
                        RepositoryListActivity.newIntent(
                            requireContext(),
                            RepositoryListType.Star(profile.username)
                        )
                    )
                }
            }
            else -> {
                ToastUtils.show(requireContext(), R.string.no_finish_yet)
            }
        }
    }

    private var getEventJob: Job? = null
    private fun getEvent() {
        getEventJob?.cancel()
        getEventJob = lifecycleScope.launch {
            profileType?.let {
                when (it) {
                    is ProfileType.User -> {
                        mViewModel.getPublicEvents(it.username)
                    }
                    is ProfileType.My -> {
                        mViewModel.getEvents(it.username)
                    }
                    is ProfileType.Organization -> {
                        mViewModel.getOrganizationEvents(it.username)
                    }
                }.collect { data ->
                    userEventItemAdapter.submitData(data)
                }
            } ?: run {
                mViewModel.getEvents().collect {
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
                profileType = ProfileType.My(it)
            })
        } else {
            profileType = argName
        }
        mBinding.apply {
            profileViewModel = mViewModel
            lifecycleOwner = this@ProfileFragment
            if (profileType is ProfileType.User || profileType is ProfileType.Organization) {
                toolbarProfile.menu.removeItem(R.id.action_settings)
                toolbarProfile.navigationIcon =
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_arrow_back_deep_orange_24dp,
                        null
                    )
                toolbarProfile.setNavigationOnClickListener {
                    requireActivity().onBackPressed()
                }
            }
            profileRefresh.setOnRefreshListener {
                mViewModel.getUserProfile(profileType).observe(viewLifecycleOwner, {
                    profileRefresh.isRefreshing = false
                })
                profileType?.let {
                    getEvent()
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
                    getEvent()
                }
            })
            toolbarProfile.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_settings -> {
                        startActivity(
                            requireContext().newIntent(AboutActivity::class.java)
                        )
                        true
                    }
                    R.id.action_share -> {
                        profileType?.let {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "https://gitee.com/" + it.username
                                )
                            }
                            startActivity(Intent.createChooser(intent, null))
                        } ?: run {
                            ToastUtils.show(requireContext(), R.string.no_finish_yet)
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
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