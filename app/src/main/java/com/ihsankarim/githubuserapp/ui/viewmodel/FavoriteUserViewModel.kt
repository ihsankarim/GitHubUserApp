package com.ihsankarim.githubuserapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihsankarim.githubuserapp.data.local.entity.FavoriteUserEntity
import com.ihsankarim.githubuserapp.data.local.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> {
        _isLoading.value = true

        val favoriteUsersLiveData = mFavoriteUserRepository.getAllFavoriteUsers()

        favoriteUsersLiveData.observeForever { favoriteUsers ->
            _isLoading.value = false
        }

        return favoriteUsersLiveData
    }
}