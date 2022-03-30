package com.susan.githubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.susan.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var accountModel: GitHubAccountViewModel
    private lateinit var accountAdapter: GitHubAccountAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "GitHub Search"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        accountAdapter = GitHubAccountAdapter()
        accountAdapter.notifyDataSetChanged()
        accountAdapter.setOnItemClickCallback(object : GitHubAccountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GitHubAccount) {
                Intent(this@MainActivity, DetailGitHubAccountActivity::class.java).also {
                    it.putExtra(DetailGitHubAccountActivity.USERNAME, data.login)
                    it.putExtra(DetailGitHubAccountActivity.ID, data.id)
                    it.putExtra(DetailGitHubAccountActivity.AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        accountModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(GitHubAccountViewModel::class.java)

        binding.apply {
            rvGithubaccount.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithubaccount.setHasFixedSize(true)
            rvGithubaccount.adapter = accountAdapter

            btnSearch.setOnClickListener {
                searchGitHubAccount()
            }

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchGitHubAccount()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        accountModel.getSearchAccounts().observe(this, {
            if (it != null) {
                accountAdapter.setListGitHubAccount(it)
                showLoading(false)
            }
        })
    }

    private fun searchGitHubAccount() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            accountModel.setSearchAccounts(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavouriteGitHubAccountActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.settings -> {
                Intent(this, SettingGitHubAppActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}