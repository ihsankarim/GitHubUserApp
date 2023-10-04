package com.ihsankarim.githubuserapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsankarim.githubuserapp.data.local.entity.FavoriteUserEntity
import com.ihsankarim.githubuserapp.data.local.repository.FavoriteUserRepository
import com.ihsankarim.githubuserapp.data.remote.response.DetailUserResponse
import com.ihsankarim.githubuserapp.data.remote.retrofit.ApiConfig
import com.ihsankarim.githubuserapp.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(
    application: Application
) : ViewModel() {
    private val apiService: ApiService = ApiConfig.getApiService()

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse>
        get() = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun insertFavoriteUser(user: FavoriteUserEntity){
        mFavoriteUserRepository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: FavoriteUserEntity) {
        mFavoriteUserRepository.deleteFavoriteUser(user)
    }

    fun getFavoriteUser(username: String): LiveData<FavoriteUserEntity> {
        return mFavoriteUserRepository.getFavoriteUser(username)
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val call = apiService.getDetailUser(username)
        call.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val detailUser = response.body()
                    detailUser?.let {
                        _detailUser.value = it
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("NetworkError", "Terjadi masalah jaringan ${t.message}")
            }
        })
    }


}
