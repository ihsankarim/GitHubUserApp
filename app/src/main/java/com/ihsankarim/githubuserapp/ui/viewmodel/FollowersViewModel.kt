package com.ihsankarim.githubuserapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsankarim.githubuserapp.data.remote.response.GithubResponse
import com.ihsankarim.githubuserapp.data.remote.retrofit.ApiConfig
import com.ihsankarim.githubuserapp.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersViewModel : ViewModel() {
    private val apiService: ApiService = ApiConfig.getApiService()

    private val _followers = MutableLiveData<List<GithubResponse>>()
    val followers: LiveData<List<GithubResponse>>
        get() = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true
        val call = apiService.getFollowers(username, "50")
        call.enqueue(object : Callback<List<GithubResponse>> {
            override fun onResponse(
                call: Call<List<GithubResponse>>,
                response: Response<List<GithubResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val followersList = response.body()
                    followersList?.let {
                        _followers.value = it
                    }
                }
            }

            override fun onFailure(call: Call<List<GithubResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e("NetworkError", "Terjadi masalah jaringan ${t.message}")
            }
        })
    }


}
