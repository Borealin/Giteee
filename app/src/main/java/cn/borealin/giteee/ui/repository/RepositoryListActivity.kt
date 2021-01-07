/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午4:01
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午4:01
 */

package cn.borealin.giteee.ui.repository

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.borealin.giteee.R
import cn.borealin.giteee.databinding.ActivityRepositoryListBinding
import cn.borealin.giteee.model.repository.RepositoryListItemCallback
import cn.borealin.giteee.ui.profile.*
import cn.borealin.giteee.utils.ToastUtils
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class RepositoryListActivity : AppCompatActivity() {
    private val mBinding: ActivityRepositoryListBinding by binding()
    private val mViewModel: RepositoryViewModel by viewModels()

    private lateinit var repositoryListType: RepositoryListType
    private lateinit var repositoryListAdapter: RepositoryListAdapter
    private var getListJob: Job? = null

    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getRepoList(repositoryListType).onCompletion {
                mBinding.eventRefresh.isRefreshing = false
            }.collect {
                repositoryListAdapter.submitData(it)
            }
        }
    }

    private val onClickListener: RepositoryListItemCallback = {
        ToastUtils.show(this, R.string.no_finish_yet)
//            repositoryListItemData ->
//        startActivity(
//            RepositoryActivity.newIntent(
//                this@RepositoryListActivity,
//                repositoryListItemData.login
//            )
//        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryListType = requireNotNull(intent.getParcelableExtra(KEY_REPOSITORY_LIST_TYPE)) {
            "repository list params is not null"
        }
        repositoryListAdapter = RepositoryListAdapter(onClickListener)
        mBinding.apply {
            repositoryViewModel = mViewModel
            lifecycleOwner = this@RepositoryListActivity
            toolbarRepositoryList.setNavigationOnClickListener {
                onBackPressed()
            }
            eventRefresh.setOnRefreshListener {
                getList()
            }
            toolbarRepositoryList.title = getString(repositoryListType.toTitleStringRes())
            toolbarRepositoryList.subtitle =
                (if (repositoryListType is RepositoryListType.Search) "about " else "") + repositoryListType.username
            repositoryListContainer.adapter = repositoryListAdapter
            getList()
        }
    }

    companion object {
        private const val KEY_REPOSITORY_LIST_TYPE = "key_repository_list_type"
        fun newIntent(context: Context, repositoryListType: RepositoryListType): Intent {
            return Intent(context, RepositoryListActivity::class.java).apply {
                putExtra(KEY_REPOSITORY_LIST_TYPE, repositoryListType)
            }
        }
    }
}

sealed class RepositoryListType : Parcelable {
    abstract val username: String

    @StringRes
    abstract fun toTitleStringRes(): Int

    @Parcelize
    data class MyRepository(override val username: String = "My") : RepositoryListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_repository_list
        }
    }

    @Parcelize
    data class OrganizationRepository(override val username: String) : RepositoryListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_repository_list
        }
    }

    @Parcelize
    data class PublicRepository(override val username: String) : RepositoryListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_repository_list
        }
    }

    @Parcelize
    data class Watch(override val username: String) : RepositoryListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_watch_list
        }
    }

    @Parcelize
    data class Star(override val username: String) : RepositoryListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_star_list
        }
    }

    @Parcelize
    data class Search(override val username: String) : RepositoryListType() {
        override fun toTitleStringRes(): Int {
            return R.string.title_repository_list
        }
    }
}