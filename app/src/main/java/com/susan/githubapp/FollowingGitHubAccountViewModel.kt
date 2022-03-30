package com.susan.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingGitHubAccountViewModel : ViewModel() {

    val listAccountFollowing = MutableLiveData<ArrayList<GitHubAccount>>()

    fun setListAccountFollowing(username: String) {
        RetrofitAccount.api_Instance
            .getGitHubFollowing(username)
            .enqueue(object : Callback<ArrayList<GitHubAccount>> {
                override fun onResponse(
                    call: Call<ArrayList<GitHubAccount>>,
                    response: Response<ArrayList<GitHubAccount>>
                ) {
                    if (response.isSuccessful) {
                        listAccountFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<GitHubAccount>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListAccountFollowing(): LiveData<ArrayList<GitHubAccount>> {
        return listAccountFollowing
    }
}