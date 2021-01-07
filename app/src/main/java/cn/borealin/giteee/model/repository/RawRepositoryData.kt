/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/7 上午10:34
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/7 上午10:34
 */

package cn.borealin.giteee.model.repository

import android.os.Parcelable
import cn.borealin.giteee.model.common.RepositoryListConverter
import cn.borealin.giteee.model.common.RepositoryListItemData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawRepositoryData(
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("full_name")
    var fullName: String,
    @Expose
    @SerializedName("human_name")
    var humanName: String,
    @Expose
    @SerializedName("description")
    var description: String?,
    @Expose
    @SerializedName("private")
    var private: Boolean,
    @Expose
    @SerializedName("public")
    var public: Boolean,
    @Expose
    @SerializedName("stargazers_count")
    var stargazerCount: Int,
    @Expose
    @SerializedName("language")
    var language: String?,
) : Parcelable, RepositoryListConverter {
    override fun toRepositoryListItemData(): RepositoryListItemData {
        return RepositoryListItemData(
            name = humanName,
            login = fullName,
            stargazer = stargazerCount,
            description = description,
            language = language
        )
    }

}