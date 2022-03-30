package com.susan.githubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.susan.githubapp.databinding.ActivityFavouriteGitHubAccountBinding

class FavouriteGitHubAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteGitHubAccountBinding
    private lateinit var accountModel: FavouriteGitHubAccountViewModel
    private lateinit var accountAdapter: GitHubAccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteGitHubAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favourite"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        accountAdapter = GitHubAccountAdapter()
        accountAdapter.notifyDataSetChanged()

        accountModel = ViewModelProvider(this).get(FavouriteGitHubAccountViewModel::class.java)

        accountAdapter.setOnItemClickCallback(object : GitHubAccountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GitHubAccount) {
                Intent(this@FavouriteGitHubAccountActivity, DetailGitHubAccountActivity::class.java).also {
                    it.putExtra(DetailGitHubAccountActivity.USERNAME, data.login)
                    it.putExtra(DetailGitHubAccountActivity.ID, data.id)
                    it.putExtra(DetailGitHubAccountActivity.AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvAccountFavourite.setHasFixedSize(true)
            rvAccountFavourite.layoutManager = LinearLayoutManager(this@FavouriteGitHubAccountActivity)
            rvAccountFavourite.adapter = accountAdapter
        }

        accountModel.getAccountFavourite()?.observe(this, {
            if (it != null) {
                val list = mapListAccount(it)
                accountAdapter.setListGitHubAccount(list)
            }
        })
    }

    private fun mapListAccount(accountFavourite: List<FavouriteGitHubAccount>): ArrayList<GitHubAccount> {
        val listFavouriteAccounts = ArrayList<GitHubAccount>()
        for (accountFavourites in accountFavourite) {
            val accountFavouriteMapped = GitHubAccount(
                accountFavourites.login,
                accountFavourites.id,
                accountFavourites.avatar_url
            )
            listFavouriteAccounts.add(accountFavouriteMapped)
        }
        return listFavouriteAccounts
    }
}