package com.ihsankarim.githubuserapp.data.remote.retrofit

import com.ihsankarim.githubuserapp.BuildConfig
import com.ihsankarim.githubuserapp.data.remote.response.DetailUserResponse
import com.ihsankarim.githubuserapp.data.remote.response.GithubResponse
import com.ihsankarim.githubuserapp.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token ghp_${BuildConfig.API_KEY}")
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") per_page: String
    ): Call<SearchResponse>

    @Headers("Authorization: token ghp_${BuildConfig.API_KEY}")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: token ghp_${BuildConfig.API_KEY}")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Query("per_page") per_page: String
    ): Call<List<GithubResponse>>

    @Headers("Authorization: token ghp_${BuildConfig.API_KEY}")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Query("per_page") per_page: String
    ): Call<List<GithubResponse>>
}