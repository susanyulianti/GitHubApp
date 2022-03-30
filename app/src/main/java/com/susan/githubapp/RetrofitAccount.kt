package com.susan.githubapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAccount {

    private const val URL = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api_Instance = retrofit.create(Api::class.java)
}