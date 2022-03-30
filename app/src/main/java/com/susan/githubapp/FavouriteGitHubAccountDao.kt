package com.susan.githubapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteGitHubAccountDao {

    @Query(value = "SELECT * from favouriteaccount_github")
    fun getFavouriteGitHubAccount(): LiveData<List<FavouriteGitHubAccount>>

    @Query(value = "SELECT count(*) from favouriteaccount_github WHERE favouriteaccount_github.id = :id")
    suspend fun checkGitHubAccount(id: Int): Int

    @Query(value = "DELETE from favouriteaccount_github WHERE favouriteaccount_github.id = :id")
    suspend fun removeFromAccountFavourite(id: Int): Int

    @Insert
    suspend fun addToFavouriteGitHubAccount(favouriteGitHubAccount: FavouriteGitHubAccount)
}