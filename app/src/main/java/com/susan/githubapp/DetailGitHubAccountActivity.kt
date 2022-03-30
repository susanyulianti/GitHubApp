package com.susan.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.susan.githubapp.databinding.ActivityDetailGitHubAccountBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailGitHubAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGitHubAccountBinding
    private lateinit var accountModel: DetailGitHubAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGitHubAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        val id = intent.getIntExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(AVATAR)
        val bundle = Bundle()
        bundle.putString(USERNAME, username)

        accountModel = ViewModelProvider(this).get(DetailGitHubAccountViewModel::class.java)
        accountModel.setDetailGitHubAccounts(username.toString())
        accountModel.getDetailGitHubAccounts().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvCompany.text = it.company
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    tvRepos.text = "${it.public_repos} Repository"
                    Glide.with(this@DetailGitHubAccountActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgUser)
                }
                showLoading(false)
            }
        })

        val sectionPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            showLoading(true)
            tab.setupWithViewPager(viewPager)
        }

        var _accountIsChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = accountModel.checkGitHubAccount(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _accountIsChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _accountIsChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _accountIsChecked = !_accountIsChecked
            if (_accountIsChecked) {
                accountModel.addAccountToFavourite(username.toString(), id, avatarUrl.toString())
            } else {
                accountModel.removeAccountFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _accountIsChecked
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val USERNAME = "username"
        const val ID = "id"
        const val AVATAR = "avatar"
    }
}