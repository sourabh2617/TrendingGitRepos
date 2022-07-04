package com.sourabhapps.trendinggitrepos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sourabhapps.trendinggitrepos.models.GitHubRepo

@Database(entities = [GitHubRepo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	
	abstract fun gitHubRepoDao(): GitHubRepoDao
}