package com.ihsankarim.githubuserapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsankarim.githubuserapp.R
import com.ihsankarim.githubuserapp.databinding.FragmentFollowingBinding
import com.ihsankarim.githubuserapp.ui.adapter.ListUserAdapter
import com.ihsankarim.githubuserapp.ui.viewmodel.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var viewModel: FollowingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME) ?: ""

        binding = FragmentFollowingBinding.bind(view)

        val rvFollowing = binding.rvFollowing
        listUserAdapter = ListUserAdapter(ArrayList()) { user ->
        }
        rvFollowing.adapter = listUserAdapter
        rvFollowing.layoutManager = LinearLayoutManager(requireActivity())

        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        viewModel.following.observe(viewLifecycleOwner) { followingList ->
            if (followingList.isNullOrEmpty()) {
                binding.tvNoFollowing.visibility = View.VISIBLE
            } else {
                binding.tvNoFollowing.visibility = View.GONE
            }
            listUserAdapter.setUserList(ArrayList(followingList))
        }

        viewModel.getFollowing(username)
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvFollowing.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvFollowing.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            fragment.arguments = args
            return fragment
        }
    }

}
