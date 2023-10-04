package com.ihsankarim.githubuserapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsankarim.githubuserapp.data.remote.response.GithubResponse
import com.ihsankarim.githubuserapp.databinding.ActivityMainBinding
import com.ihsankarim.githubuserapp.ui.SettingsPreferences
import com.ihsankarim.githubuserapp.ui.adapter.ListUserAdapter
import com.ihsankarim.githubuserapp.ui.dataStore
import com.ihsankarim.githubuserapp.ui.viewmodel.MainViewModel
import com.ihsankarim.githubuserapp.ui.viewmodel.SettingsViewModel
import com.ihsankarim.githubuserapp.ui.viewmodelfactory.SettingsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = ListUserAdapter(
            viewModel.userList.value?.toCollection(ArrayList()) ?: ArrayList()
        ) { user ->
            getDetailUser(user)
        }
        binding.rvUser.adapter = adapter
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                viewModel.searchUsers(query, "50")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

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

        viewModel.userList.observe(this) { userList ->
            adapter.setUserList(userList as ArrayList<GithubResponse>)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.fetchUserList()

        binding.ivSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.ivFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    private fun getDetailUser(user: GithubResponse) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
        intent.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, user.avatarUrl)
        startActivity(intent)
    }

}

