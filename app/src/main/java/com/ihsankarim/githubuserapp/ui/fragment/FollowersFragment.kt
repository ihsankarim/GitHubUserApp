package com.ihsankarim.githubuserapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsankarim.githubuserapp.R
import com.ihsankarim.githubuserapp.databinding.FragmentFollowersBinding
import com.ihsankarim.githubuserapp.ui.adapter.ListUserAdapter
import com.ihsankarim.githubuserapp.ui.viewmodel.FollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var viewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME) ?: ""

        binding = FragmentFollowersBinding.bind(view)

        val rvFollowers = binding.rvFollowers
        listUserAdapter = ListUserAdapter(ArrayList()) { user ->
        }

        rvFollowers.adapter = listUserAdapter
        rvFollowers.layoutManager = LinearLayoutManager(requireActivity())

        viewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        viewModel.followers.observe(viewLifecycleOwner) { followersList ->
            if (followersList.isNullOrEmpty()) {
                binding.tvNoFollowers.visibility = View.VISIBLE
            } else {
                binding.tvNoFollowers.visibility = View.GONE
            }
            listUserAdapter.setUserList(ArrayList(followersList))

        }

        viewModel.getFollowers(username)

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvFollowers.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvFollowers.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        private const val ARG_USERNAME = "arg_username"

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            fragment.arguments = args
            return fragment
        }
    }

}