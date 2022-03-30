package com.susan.githubapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.susan.githubapp.databinding.FollowFragmentBinding

class FollowersGitHubAccountFragment : Fragment(R.layout.follow_fragment) {

    private var _binding: FollowFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowGitHubAccountViewModel
    private lateinit var adapter: GitHubAccountAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailGitHubAccountActivity.USERNAME).toString()
        _binding = FollowFragmentBinding.bind(view)

        adapter = GitHubAccountAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvGithubaccount.setHasFixedSize(true)
            rvGithubaccount.layoutManager = LinearLayoutManager(activity)
            rvGithubaccount.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowGitHubAccountViewModel::class.java)
        viewModel.setListAccountFollowers(username)
        viewModel.getListAccountFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setListGitHubAccount(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}