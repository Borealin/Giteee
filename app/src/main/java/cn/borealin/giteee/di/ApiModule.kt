/*
 * Created by Borealin (308704199deniel@gmail.com) on 2020/12/31 下午11:40
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 2020/12/31 下午11:40
 */

package cn.borealin.giteee.di

import cn.borealin.giteee.BuildConfig
import cn.borealin.giteee.api.interfaces.GiteeApi
import cn.borealin.giteee.api.interfaces.OAuthApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {
    private val API_ENDPOINT: String
        get() {
            return if (BuildConfig.DEBUG) TEST_BASE_URL else BASE_URL
        }
    private const val TEST_BASE_URL = "https://gitee.com/api/v5/"
    private const val BASE_URL = "https://gitee.com/api/v5/"

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        return dispatcher
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: Interceptor, dispatcher: Dispatcher): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .dispatcher(dispatcher)
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(API_ENDPOINT)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideOAuthService(retrofit: Retrofit): OAuthApi = retrofit.create(OAuthApi::class.java)


    @Provides
    @Singleton
    fun provideGiteeService(retrofit: Retrofit): GiteeApi = retrofit.create(GiteeApi::class.java)

}