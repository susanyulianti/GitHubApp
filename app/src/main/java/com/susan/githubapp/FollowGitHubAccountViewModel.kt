package com.susan.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowGitHubAccountViewModel : ViewModel() {
    val listAccountFollowers = MutableLiveData<ArrayList<GitHubAccount>>()

    fun setListAccountFollowers(username: String) {
        RetrofitAccount.api_Instance
            .getGitHubFollowers(username)
            .enqueue(object : Callback<ArrayList<GitHubAccount>> {
                override fun onResponse(
                    call: Call<ArrayList<GitHubAccount>>,
                    response: Response<ArrayList<GitHubAccount>>
                ) {
                    if (response.isSuccessful) {
                        listAccountFollowers.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<ArrayList<GitHubAccount>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getListAccountFollowers(): LiveData<ArrayList<GitHubAccount>> {
        return listAccountFollowers
    }
}
