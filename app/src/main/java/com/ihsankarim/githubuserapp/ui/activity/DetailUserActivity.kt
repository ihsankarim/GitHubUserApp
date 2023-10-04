package com.ihsankarim.githubuserapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ihsankarim.githubuserapp.R
import com.ihsankarim.githubuserapp.data.local.entity.FavoriteUserEntity
import com.ihsankarim.githubuserapp.data.remote.response.DetailUserResponse
import com.ihsankarim.githubuserapp.databinding.ActivityDetailUserBinding
import com.ihsankarim.githubuserapp.ui.adapter.SectionsPagerAdapter
import com.ihsankarim.githubuserapp.ui.fragment.FollowersFragment
import com.ihsankarim.githubuserapp.ui.fragment.FollowingFragment
import com.ihsankarim.githubuserapp.ui.viewmodel.DetailUserViewModel
import com.ihsankarim.githubuserapp.ui.viewmodelfactory.FavoriteViewModelFactory

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    private var isFavorite = false
    private var username: String? = null
    private var avatarUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = obtainViewModel(this@DetailUserActivity)

        username = intent.getStringExtra(EXTRA_USERNAME)
        avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)

        if (username != null) {
            viewModel.getDetailUser(username!!)
        }

        viewModel.detailUser.observe(this) { detailUser ->
            if (detailUser != null) {
                showUserDetail(detailUser)

                val nonNullableUsername = username ?: "default_value"

                val followersFragment = FollowersFragment.newInstance(nonNullableUsername)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, followersFragment)
                    .commit()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val tabs = findViewById<TabLayout>(R.id.tabs)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.followers)
                1 -> tab.text = getString(R.string.following)
            }
        }.attach()

        val nonNullableUsername = username ?: "default_value"
        val followersFragment = FollowersFragment.newInstance(nonNullableUsername)
        val followingFragment = FollowingFragment.newInstance(nonNullableUsername)

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, followersFragment)
                            .commit()
                    }

                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, followingFragment)
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.ivSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Check if the user is already a favorite
        viewModel.getFavoriteUser(username ?: "").observe(this) { favoriteUser ->
            isFavorite = favoriteUser != null
            updateFavoriteButtonUI()
        }

        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                Toast.makeText(
                    applicationContext,
                    "User removed from favorites!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.deleteFavoriteUser(FavoriteUserEntity(username ?: "", avatarUrl))
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                Toast.makeText(applicationContext, "User added to favorites!", Toast.LENGTH_SHORT)
                    .show()
                viewModel.insertFavoriteUser(FavoriteUserEntity(username ?: "", avatarUrl))
            }
            isFavorite = !isFavorite
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun showUserDetail(detailUser: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailUser.avatarUrl)
                .placeholder(R.drawable.ic_users)
                .into(imgUser)
            tvName.text = detailUser.name
            tvUsername.text = detailUser.login
            tvFollowers.text = "${detailUser.followers} ${getString(R.string.followers)}"
            tvFollowing.text = "${detailUser.following} ${getString(R.string.following)}"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.tabs.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
            binding.cardView.visibility = View.GONE
            binding.fabFavorite.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.tabs.visibility = View.VISIBLE
            binding.viewPager.visibility = View.VISIBLE
            binding.cardView.visibility = View.VISIBLE
            binding.fabFavorite.visibility = View.VISIBLE
        }
    }

    private fun updateFavoriteButtonUI() {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }
}
