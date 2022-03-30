package com.susan.githubapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavouriteGitHubAccountViewModel(application: Application) : AndroidViewModel(application) {
    private var accountGitHubDao: FavouriteGitHubAccountDao?
    private var databaseAccountGitHub: AccountGitHubDatabase?

    init {
        databaseAccountGitHub = AccountGitHubDatabase.getGitHubAccountDatabase(application)
        accountGitHubDao = databaseAccountGitHub?.favouriteGitHubAccountDao()
    }

    fun getAccountFavourite(): LiveData<List<FavouriteGitHubAccount>>? {
        return accountGitHubDao?.getFavouriteGitHubAccount()
    }
}