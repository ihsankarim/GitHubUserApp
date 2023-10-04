package com.ihsankarim.githubuserapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.ihsankarim.githubuserapp.R
import com.ihsankarim.githubuserapp.ui.SettingsPreferences
import com.ihsankarim.githubuserapp.ui.dataStore
import com.ihsankarim.githubuserapp.ui.viewmodel.SettingsViewModel
import com.ihsankarim.githubuserapp.ui.viewmodelfactory.SettingsViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var imgSplash: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imgSplash = findViewById(R.id.img_splash)

        imgSplash.alpha = 0f
        imgSplash.animate().setDuration(1500).alpha(1f).start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)

        val pref = SettingsPreferences.getInstance(application.dataStore)
        val settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        ).get(SettingsViewModel::class.java)

        settingsViewModel.getThemeSettings().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    companion object {
        private val splashTimeOut: Long = 2000
    }
}