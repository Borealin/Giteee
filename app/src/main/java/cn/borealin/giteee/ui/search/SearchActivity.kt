/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午8:29
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午8:29
 */

package cn.borealin.giteee.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.borealin.giteee.databinding.ActivitySearchBinding
import cn.borealin.giteee.ui.issue.IssueListActivity
import cn.borealin.giteee.ui.issue.IssueListType
import cn.borealin.giteee.ui.profile.ProfileListActivity
import cn.borealin.giteee.ui.profile.ProfileListType
import cn.borealin.giteee.ui.repository.RepositoryListActivity
import cn.borealin.giteee.ui.repository.RepositoryListType
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val mBinding: ActivitySearchBinding by binding()
    private val mViewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            searchViewModel = mViewModel
            lifecycleOwner = this@SearchActivity
            toolbarSearchList.setNavigationOnClickListener {
                onBackPressed()
            }
            searchRepo.setOnClickListener {
                startActivity(
                    mViewModel.inputContentStatic.value?.let {
                        RepositoryListActivity.newIntent(
                            this@SearchActivity,
                            RepositoryListType.Search(it)
                        )
                    }
                )
            }
            searchIssue.setOnClickListener {
                startActivity(
                    mViewModel.inputContentStatic.value?.let {
                        IssueListActivity.newIntent(
                            this@SearchActivity,
                            IssueListType.Search(it)
                        )
                    }
                )
            }
            searchProfile.setOnClickListener {
                startActivity(
                    mViewModel.inputContentStatic.value?.let {
                        ProfileListActivity.newIntent(
                            this@SearchActivity,
                            ProfileListType.Search(it)
                        )
                    }
                )
            }
        }
    }
}