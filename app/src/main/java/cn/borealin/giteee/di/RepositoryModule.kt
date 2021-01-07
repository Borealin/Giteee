/*
 * Created by Borealin (308704199deniel@gmail.com) on 2020/12/31 下午11:26
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 2020/12/31 下午11:26
 */

package cn.borealin.giteee.di

import cn.borealin.giteee.api.interfaces.*
import cn.borealin.giteee.data.UserPreference
import cn.borealin.giteee.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideLoginRepository(
        oAuthApi: OAuthApi,
        userPreference: UserPreference
    ): LoginRepository = RepositoryFactory.makeLoginRepository(oAuthApi, userPreference)

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileApi: ProfileApi,
        repositoryApi: RepositoryApi,
        userPreference: UserPreference
    ): ProfileRepository =
        RepositoryFactory.makeProfileRepository(profileApi, repositoryApi, userPreference)

    @Singleton
    @Provides
    fun provideActivityRepository(
        activityApi: ActivityApi,
        userPreference: UserPreference
    ): ActivityRepository = RepositoryFactory.makeActivityRepository(activityApi, userPreference)

    @Singleton
    @Provides
    fun provideRepositoryRepository(
        repositoryApi: RepositoryApi,
        userPreference: UserPreference
    ): RepositoryRepository =
        RepositoryFactory.makeRepositoryRepository(repositoryApi, userPreference)

    @Singleton
    @Provides
    fun provideIssueRepository(
        issueApi: IssueApi,
        userPreference: UserPreference
    ): IssueRepository =
        RepositoryFactory.makeIssueRepository(issueApi, userPreference)
}