package com.susan.githubapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    fun getSearchGitHubAccount(
        @Query("q") query: String
    ): Call<GitHubAccountResponse>

    @GET("users/{username}")
    fun getDetailGitHubAccount(
        @Path("username") username: String
    ): Call<DetailGitHubAccountResponse>

    @GET("users/{username}/followers")
    fun getGitHubFollowers(
        @Path("username") username: String
    ): Call<ArrayList<GitHubAccount>>

    @GET("users/{username}/following")
    fun getGitHubFollowing(
        @Path("username") username: String
    ): Call<ArrayList<GitHubAccount>>
}