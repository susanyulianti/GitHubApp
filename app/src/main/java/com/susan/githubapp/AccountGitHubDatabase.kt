package com.susan.githubapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteGitHubAccount::class],
    version = 1
)
abstract class AccountGitHubDatabase: RoomDatabase() {
    companion object {
        var INSTANCE: AccountGitHubDatabase? = null

        fun getGitHubAccountDatabase(context: Context): AccountGitHubDatabase? {
            if (INSTANCE == null) {
                synchronized(AccountGitHubDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AccountGitHubDatabase::class.java,
                        "databaseaccount_github"
                    ).build()
                }
            }

            return INSTANCE
        }
    }

    abstract fun favouriteGitHubAccountDao(): FavouriteGitHubAccountDao
}
