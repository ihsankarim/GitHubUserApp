package com.ihsankarim.githubuserapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsankarim.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.ihsankarim.githubuserapp.ui.adapter.FavoriteUserAdapter
import com.ihsankarim.githubuserapp.ui.viewmodel.FavoriteUserViewModel
import com.ihsankarim.githubuserapp.ui.viewmodelfactory.FavoriteViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteUserAdapter

    private lateinit var viewModel: FavoriteUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@FavoriteUserActivity)
        adapter = FavoriteUserAdapter()

        binding.apply {
            rvFavoriteUsers.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvFavoriteUsers.adapter = adapter
        }

        viewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })

        viewModel.getAllFavoriteUsers().observe(this, { favoriteUsers ->
            adapter.submitList(favoriteUsers)
        })

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.ivSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        Log.d("FavoriteUserActivity", "isLoading: $isLoading")
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvFavoriteUsers.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvFavoriteUsers.visibility = View.VISIBLE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = FavoriteViewModelFactory.getInstance(application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }
}
