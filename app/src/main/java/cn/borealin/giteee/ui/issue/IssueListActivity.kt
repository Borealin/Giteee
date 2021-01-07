/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 下午12:28
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 下午12:28
 */

package cn.borealin.giteee.ui.issue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.ActivityIssueListBinding
import cn.borealin.giteee.model.issue.IssueListItemCallback
import cn.borealin.giteee.utils.ToastUtils
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class IssueListActivity : AppCompatActivity() {

    private val mBinding: ActivityIssueListBinding by binding()
    private val mViewModel: IssueViewModel by viewModels()
    private lateinit var issueListType: IssueListType
    private lateinit var issueListAdapter: IssueListAdapter
    private var getListJob: Job? = null

    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getIssueList(issueListType).onCompletion {
                mBinding.eventRefresh.isRefreshing = false
            }.collect {
                issueListAdapter.submitData(it)
            }
        }
    }

    private val onClickListener: IssueListItemCallback = {
        ToastUtils.show(this, R.string.no_finish_yet)
//            issueListItemData ->
//        startActivity(
//            IssueActivity.newIntent(
//                this@RepositoryListActivity,
//                issueListItemData
//            )
//        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        issueListType = requireNotNull(intent.getParcelableExtra(KEY_ISSUE_LIST_TYPE)) {
            "profile list params is not null"
        }
        issueListAdapter = IssueListAdapter(onClickListener)
        mBinding.apply {
            issueViewModel = mViewModel
            lifecycleOwner = this@IssueListActivity
            toolbarIssueList.setNavigationOnClickListener {
                onBackPressed()
            }
            toolbarIssueList.subtitle =
                if (issueListType is IssueListType.Search) "about " + issueListType.username else ""
            eventRefresh.setOnRefreshListener {
                getList()
            }
            issueListContainer.adapter = issueListAdapter
            getList()
        }
        ToastUtils.show(this, R.string.reason_get_issue_not_complete)
    }

    companion object {
        private const val KEY_ISSUE_LIST_TYPE = "key_issue_list_type"
        fun newIntent(context: Context, issueListType: IssueListType): Intent {
            return Intent(context, IssueListActivity::class.java).apply {
                putExtra(KEY_ISSUE_LIST_TYPE, issueListType)
            }
        }
    }
}

sealed class IssueListType : Parcelable {
    abstract val username: String

    @Parcelize
    data class My(override val username: String = "") : IssueListType()

    @Parcelize
    data class Search(override val username: String) : IssueListType()

}