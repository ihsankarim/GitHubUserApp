package com.ihsankarim.githubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

    @SerializedName("following_url")
    val followingUrl: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("login")
    val login: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("gravatar_id")
    val gravatarId: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("organizations_url")
    val organizationsUrl: String?,

    @SerializedName("hireable")
    val hireable: String,

    @SerializedName("starred_url")
    val starredUrl: String?,

    @SerializedName("followers_url")
    val followersUrl: String?,

    @SerializedName("public_gists")
    val publicGists: Int,

    @SerializedName("url")
    val url: String?,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("avatar_url")
    val avatarUrl: String?,


    @SerializedName("html_url")
    val htmlUrl: String?,

    @SerializedName("following")
    val following: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("location")
    val location: String?
)

