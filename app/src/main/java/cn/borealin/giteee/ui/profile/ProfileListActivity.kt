/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午10:26
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午10:26
 */

package cn.borealin.giteee.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.ActivityProfileListBinding
import cn.borealin.giteee.model.users.ProfileListItemCallback
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class ProfileListActivity : AppCompatActivity() {

    private val mBinding: ActivityProfileListBinding by binding()
    private val mViewModel: ProfileViewModel by viewModels()
    private lateinit var profileListType: ProfileListType
    private lateinit var profileListAdapter: ProfileListAdapter
    private var getListJob: Job? = null

    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getList(profileListType).onCompletion {
                mBinding.eventRefresh.isRefreshing = false
            }.collect {
                profileListAdapter.submitData(it)
            }
        }
    }

    private val onClickListener: ProfileListItemCallback = { profileListItemData ->
        when (profileListType) {
            is ProfileListType.Follower, is ProfileListType.Following, is ProfileListType.Member -> {
                startActivity(
                    ProfileActivity.newIntent(
                        this@ProfileListActivity,
                        ProfileType.User(profileListItemData.login)
                    )
                )
            }
            is ProfileListType.Organization -> {
                startActivity(
                    ProfileActivity.newIntent(
                        this@ProfileListActivity,
                        ProfileType.Organization(profileListItemData.login)
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileListType = requireNotNull(intent.getParcelableExtra(KEY_PROFILE_LIST_TYPE)) {
            "profile list params is not null"
        }
        profileListAdapter = ProfileListAdapter(onClickListener)
        mBinding.apply {
            profileViewModel = mViewModel
            lifecycleOwner = this@ProfileListActivity
            toolbarProfileList.setNavigationOnClickListener {
                onBackPressed()
            }
            eventRefresh.setOnRefreshListener {
                getList()
            }
            toolbarProfileList.title = getString(profileListType.toTitleStringRes())
            toolbarProfileList.subtitle = profileListType.username
            profileListContainer.adapter = profileListAdapter
            getList()
        }
    }

    companion object {
        private const val KEY_PROFILE_LIST_TYPE = "key_profile_list_type"
        fun newIntent(context: Context, profileListType: ProfileListType): Intent {
            return Intent(context, ProfileListActivity::class.java).apply {
                putExtra(KEY_PROFILE_LIST_TYPE, profileListType)
            }
        }
    }
}


sealed class ProfileListType : Parcelable {
    abstract val username: String

    @StringRes
    abstract fun toTitleStringRes(): Int

    @Parcelize
    data class Follower(override val username: String) : ProfileListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_follower_list
        }
    }

    @Parcelize
    data class Following(override val username: String) : ProfileListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_following_list
        }
    }

    @Parcelize
    data class Organization(override val username: String) : ProfileListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_organization_list
        }
    }

    @Parcelize
    data class Member(override val username: String) : ProfileListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_member_list
        }
    }
}