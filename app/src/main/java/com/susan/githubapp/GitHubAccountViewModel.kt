package com.susan.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubAccountViewModel : ViewModel() {

    val list_Accountgithub = MutableLiveData<ArrayList<GitHubAccount>>()

    fun setSearchAccounts(query: String) {
        RetrofitAccount.api_Instance
            .getSearchGitHubAccount(query)
            .enqueue(object : Callback<GitHubAccountResponse> {
                override fun onResponse(
                    call: Call<GitHubAccountResponse>,
                    response: Response<GitHubAccountResponse>
                ) {
                    if (response.isSuccessful) {
                        list_Accountgithub.postValue(response.body()?.items)
                    }
                }
                override fun onFailure(call: Call<GitHubAccountResponse>, t: Throwable) {
                    Log.d("Search Failure", t.message.toString())
                }
            })
    }

    fun getSearchAccounts(): LiveData<ArrayList<GitHubAccount>> {
        return list_Accountgithub
    }
}