package com.ihsankarim.githubuserapp.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ihsankarim.githubuserapp.ui.SettingsPreferences
import com.ihsankarim.githubuserapp.ui.activity.SplashScreenActivity
import com.ihsankarim.githubuserapp.ui.viewmodel.SettingsViewModel

class SettingsViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            return SettingsViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(SplashScreenActivity::class.java)){
            return SplashScreenActivity() as T
        }
        throw IllegalAccessException("Unknown ViewModel class" + modelClass.name)
    }
}