/*
 * Created by Borealin (308704199deniel@gmail.com) on 20-12-21 下午3:54
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 20-12-21 下午3:53
 */

package cn.borealin.giteee.di

import android.content.Context
import cn.borealin.giteee.data.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class PreferenceModule {
    @Provides
    @Singleton
    fun providesUserPreference(@ApplicationContext context: Context): UserPreference =
        UserPreference(context)

}