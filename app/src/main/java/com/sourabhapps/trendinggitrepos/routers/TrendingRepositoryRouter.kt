package com.sourabhapps.trendinggitrepos.routers

import com.sourabhapps.trendinggitrepos.interfaces.HttpEventTracker
import com.sourabhapps.trendinggitrepos.interfaces.HttpOperationCallback
import com.sourabhapps.trendinggitrepos.interfaces.TrendingRepositoryApiCallback
import com.sourabhapps.trendinggitrepos.models.GitHubRepo
import com.sourabhapps.trendinggitrepos.networks.HttpOperationWrapper
import com.sourabhapps.trendinggitrepos.utilities.NetworkUtils
import okhttp3.ResponseBody
import retrofit2.Call

/**
 * Router class for handling Trending repository API network calls.
 */
class TrendingRepositoryRouter(private val eventTracker: HttpEventTracker<ArrayList<GitHubRepo>>) : HttpOperationCallback<ArrayList<GitHubRepo>> {
	
	private lateinit var call: Call<ArrayList<GitHubRepo>>
	
	private val trendingRepositoryApiCallback: TrendingRepositoryApiCallback by lazy {
		NetworkUtils.retrofitBuilder.build().create(TrendingRepositoryApiCallback::class.java)
	}
	
	/**
	 * Initialize the call.
	 */
	fun init() {
		call = trendingRepositoryApiCallback.getRepositories()
		HttpOperationWrapper<ArrayList<GitHubRepo>>().initCall(call, this)
	}
	
	/**
	 * Cancel ongoing retrofit call.
	 */
	fun stop() {
		if (this::call.isInitialized && call.isExecuted && !call.isCanceled) {
			call.cancel()
		}
	}
	
	override fun onResponse(
			call: Call<ArrayList<GitHubRepo>>,
			result: ArrayList<GitHubRepo>?,
			errorPair: Pair<String, Throwable>,
			errorBody: ResponseBody?
	) {
		when {
			result != null -> eventTracker.onCallSuccess(result)
			else -> eventTracker.onCallFail(errorPair.first, errorPair.second, errorBody)
		}
	}
}