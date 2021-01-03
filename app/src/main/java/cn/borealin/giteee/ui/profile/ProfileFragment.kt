/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/3 下午7:01
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/3 下午7:01
 */

package cn.borealin.giteee.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.FragmentProfileBinding
import com.hi.dhl.jdatabinding.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

enum class ProfileType : Serializable {
    PERSON,
    ORGANIZATION
}

@AndroidEntryPoint
class ProfileFragment : DataBindingFragment(R.layout.fragment_profile) {
    private val mBinding: FragmentProfileBinding by binding()
    private val mViewModel: ProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            profileViewModel = mViewModel
            lifecycleOwner = this@ProfileFragment
        }
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