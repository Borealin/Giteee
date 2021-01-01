/*
 * Created by Borealin (308704199deniel@gmail.com) on 2020/12/31 下午11:28
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 2020/12/31 下午11:28
 */

package cn.borealin.giteee.data

import androidx.paging.PagingConfig
import cn.borealin.giteee.api.interfaces.GiteeApi
import cn.borealin.giteee.api.interfaces.OAuthApi

object RepositoryFactory {
    fun makeRepository(
        oAuthApi: OAuthApi,
        giteeApi: GiteeApi,
        userPreference: UserPreference
    ): Repository = RepositoryImpl(
        oAuthApi,
        giteeApi,
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