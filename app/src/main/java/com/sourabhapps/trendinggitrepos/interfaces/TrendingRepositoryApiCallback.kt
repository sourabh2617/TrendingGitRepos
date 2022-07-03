package com.sourabhapps.trendinggitrepos.interfaces

import com.sourabhapps.trendinggitrepos.models.GitHubRepo
import retrofit2.Call
import retrofit2.http.GET

interface TrendingRepositoryApiCallback {
	
	@GET("repositories")
	fun getRepositories(): Call<ArrayList<GitHubRepo>>
}