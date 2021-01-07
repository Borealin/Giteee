/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午1:52
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午1:52
 */

package cn.borealin.giteee.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.ActivityProfileBinding
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val mBinding: ActivityProfileBinding by binding()
    private lateinit var profileType: ProfileType
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileType =
            requireNotNull(intent.getParcelableExtra(KEY_PROFILE_TYPE)) {
                "profile params is not null"
            }
        mBinding.apply {
            ProfileFragment.addFragment(
                supportFragmentManager,
                profileType,
                R.id.profile_fragment_container
            )
        }
    }

    companion object {
        private const val KEY_PROFILE_TYPE = "key_profile_type"
        fun newIntent(context: Context, profileType: ProfileType): Intent {
            return Intent(context, ProfileActivity::class.java).apply {
                putExtra(KEY_PROFILE_TYPE, profileType)
            }
        }
    }
}

sealed class ProfileType : Parcelable {
    abstract val username: String

    @Parcelize
    data class User(override val username: String) : ProfileType()

    @Parcelize
    data class Organization(override val username: String) : ProfileType()
}