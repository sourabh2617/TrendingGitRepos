package com.sourabhapps.trendinggitrepos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sourabhapps.trendinggitrepos.models.GitHubRepo
import com.sourabhapps.trendinggitrepos.utilities.TABLE_GITHUB_REPO

@Dao
interface GitHubRepoDao {
	
	@Query("SELECT * FROM $TABLE_GITHUB_REPO")
	fun getAllRepositories(): LiveData<List<GitHubRepo>>
	
	@Insert
	fun insertAllRepositories(repos: List<GitHubRepo>)
	
	@Query("DELETE FROM $TABLE_GITHUB_REPO")
	fun deleteAllRepositories()
}