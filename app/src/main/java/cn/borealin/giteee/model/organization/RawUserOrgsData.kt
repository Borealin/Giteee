package cn.borealin.giteee.model.organization

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawUserOrgsData(
    @Expose
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("events_url")
    val eventsUrl: String,
    @Expose
    @SerializedName("follow_count")
    val followCount: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("members_url")
    val membersUrl: String,
    @Expose
    @SerializedName("repos_url")
    val reposUrl: String,
    @Expose
    @SerializedName("url")
    val url: String,
) : Parcelable