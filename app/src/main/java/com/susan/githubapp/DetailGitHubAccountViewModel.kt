package com.susan.githubapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailGitHubAccountViewModel (application: Application) : AndroidViewModel(application) {

    val githubaccount = MutableLiveData<DetailGitHubAccountResponse>()
    private var accountGitHubDao: FavouriteGitHubAccountDao?
    private var databaseAccountGitHub: AccountGitHubDatabase?

    init {
        databaseAccountGitHub = AccountGitHubDatabase.getGitHubAccountDatabase(application)
        accountGitHubDao = databaseAccountGitHub?.favouriteGitHubAccountDao()
    }

    fun setDetailGitHubAccounts(username: String) {
        RetrofitAccount.api_Instance
            .getDetailGitHubAccount(username)
            .enqueue(object : Callback<DetailGitHubAccountResponse> {
                override fun onResponse(
                    call: Call<DetailGitHubAccountResponse>,
                    response: Response<DetailGitHubAccountResponse>
                ) {
                    if (response.isSuccessful) {
                        githubaccount.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailGitHubAccountResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getDetailGitHubAccounts(): LiveData<DetailGitHubAccountResponse> {
        return githubaccount
    }

    fun addAccountToFavourite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val accountGitHub = FavouriteGitHubAccount(
                id,
                username,
                avatarUrl
            )
            accountGitHubDao?.addToFavouriteGitHubAccount(accountGitHub)
        }
    }

    suspend fun checkGitHubAccount(id: Int) = accountGitHubDao?.checkGitHubAccount(id)

    fun removeAccountFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            accountGitHubDao?.removeFromAccountFavourite(id)
        }
    }
}