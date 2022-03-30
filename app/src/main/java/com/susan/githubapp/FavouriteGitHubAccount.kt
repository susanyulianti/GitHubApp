package com.susan.githubapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favouriteaccount_github")
data class FavouriteGitHubAccount(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String
): Serializable
