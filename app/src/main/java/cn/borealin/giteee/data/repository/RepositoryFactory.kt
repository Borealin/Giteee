/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午4:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午1:36
 */

package cn.borealin.giteee.data.repository

import androidx.paging.PagingConfig
import cn.borealin.giteee.api.interfaces.*
import cn.borealin.giteee.data.UserPreference

object RepositoryFactory {
    fun makeLoginRepository(
        oAuthApi: OAuthApi,
        userPreference: UserPreference
    ): LoginRepository = LoginRepositoryImpl(
        oAuthApi,
        userPreference
    )

    fun makeProfileRepository(
        profileApi: ProfileApi,
        repositoryApi: RepositoryApi,
        searchApi: SearchApi,
        userPreference: UserPreference
    ): ProfileRepository = ProfileRepositoryImpl(
        profileApi,
        repositoryApi,
        searchApi,
        userPreference,
        pagingConfig
    )

    fun makeActivityRepository(
        activityApi: ActivityApi,
        userPreference: UserPreference
    ): ActivityRepository = ActivityRepositoryImpl(
        activityApi,
        userPreference,
        pagingConfig
    )

    fun makeRepositoryRepository(
        repositoryApi: RepositoryApi,
        searchApi: SearchApi,
        userPreference: UserPreference
    ): RepositoryRepository = RepositoryRepositoryImpl(
        repositoryApi,
        searchApi,
        userPreference,
        pagingConfig
    )

    fun makeIssueRepository(
        issueApi: IssueApi,
        searchApi: SearchApi,
        userPreference: UserPreference
    ): IssueRepository = IssueRepositoryImpl(
        issueApi,
        searchApi,
        userPreference,
        pagingConfig
    )

    private val pagingConfig = PagingConfig(
        pageSize = 20,

        enablePlaceholders = true,

        prefetchDistance = 4,

        initialLoadSize = 20
    )
}