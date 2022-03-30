package com.susan.githubapp

data class DetailGitHubAccountResponse(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val company : String,
    val followers : Int,
    val public_repos : Int,
    val following : Int
)
