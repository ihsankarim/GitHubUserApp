package com.ihsankarim.githubuserapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsankarim.githubuserapp.data.remote.response.GithubResponse
import com.ihsankarim.githubuserapp.data.remote.response.SearchResponse
import com.ihsankarim.githubuserapp.data.remote.retrofit.ApiConfig
import com.ihsankarim.githubuserapp.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<GithubResponse>>()
    val userList: LiveData<List<GithubResponse>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val apiService: ApiService = ApiConfig.getApiService()

    fun fetchUserList() {
        _isLoading.value = true
        val call = apiService.searchUsers("abc", "50")
        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val userListResponse = response.body()
                    if (userListResponse != null) {
                        _userList.value = userListResponse.items
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("NetworkError", "Terjadi masalah jaringan ${t}")
            }
        })
    }

    fun searchUsers(query: String, per_page: String) {
        _isLoading.value = true
        val call = apiService.searchUsers(query, per_page)
        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userListResponse = response.body()
                    if (userListResponse != null) {
                        _userList.value = userListResponse.items
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("NetworkError", "Terjadi masalah jaringan ${t.message}")
            }
        })
    }
}