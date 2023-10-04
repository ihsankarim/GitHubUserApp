package com.ihsankarim.githubuserapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ihsankarim.githubuserapp.ui.fragment.FollowersFragment
import com.ihsankarim.githubuserapp.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment()
            1 -> FollowingFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}