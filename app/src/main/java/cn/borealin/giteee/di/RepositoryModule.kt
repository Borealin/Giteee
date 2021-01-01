/*
 * Created by Borealin (308704199deniel@gmail.com) on 2020/12/31 下午11:26
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 2020/12/31 下午11:26
 */

package cn.borealin.giteee.di

import cn.borealin.giteee.api.interfaces.GiteeApi
import cn.borealin.giteee.api.interfaces.OAuthApi
import cn.borealin.giteee.data.Repository
import cn.borealin.giteee.data.RepositoryFactory
import cn.borealin.giteee.data.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        oAuthApi: OAuthApi,
        giteeApi: GiteeApi,
        userPreference: UserPreference
    ): Repository = RepositoryFactory.makeRepository(oAuthApi, giteeApi, userPreference)
}