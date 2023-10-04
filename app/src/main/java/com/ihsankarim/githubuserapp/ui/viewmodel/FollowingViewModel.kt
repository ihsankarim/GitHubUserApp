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

class FollowingViewModel : ViewModel() {

    private val apiService: ApiService = ApiConfig.getApiService()

    private val _following = MutableLiveData<List<GithubResponse>>()
    val following: LiveData<List<GithubResponse>>
        get() = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getFollowing(username: String) {
        _isLoading.value = true
        val call = apiService.getFollowing(username,"50")
        call.enqueue(object : Callback<List<GithubResponse>> {
            override fun onResponse(
                call: Call<List<GithubResponse>>,
                response: Response<List<GithubResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val followingList = response.body()
                    followingList?.let {
                        _following.value = it
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
