package com.sourabhapps.trendinggitrepos.repositories

import android.os.AsyncTask
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sourabhapps.trendinggitrepos.App
import com.sourabhapps.trendinggitrepos.R
import com.sourabhapps.trendinggitrepos.interfaces.HttpEventTracker
import com.sourabhapps.trendinggitrepos.models.GitHubRepo
import com.sourabhapps.trendinggitrepos.routers.TrendingRepositoryRouter
import com.sourabhapps.trendinggitrepos.utilities.PREF_KEY_DATA_EXPIRE_TIME
import com.sourabhapps.trendinggitrepos.utilities.Utils
import okhttp3.ResponseBody
import org.jetbrains.anko.defaultSharedPreferences
import java.util.concurrent.TimeUnit.*

object TrendingRepository : HttpEventTracker<ArrayList<GitHubRepo>> {
	
	/**
	 * GitHubRepo data observer.
	 */
	private val liveData: MutableLiveData<Triple<String, String, ArrayList<GitHubRepo>>> = MutableLiveData()
	/**
	 * Router class responsible for handling trending repository network calls.
	 */
	private val trendingRepositoryRouter: TrendingRepositoryRouter = TrendingRepositoryRouter(this)
	
	/**
	 * @return GitHubRepo data observer instance.
	 */
	fun getLiveData() = liveData
	
	/**
	 * Initialize network call for fetching trending repositories.
	 */
	fun getRepositories(
			forceUpdate: Boolean,
			owner: LifecycleOwner
	) {
		if (forceUpdate) {
			trendingRepositoryRouter.init()
		} else {
			App.getAppDatabase().gitHubRepoDao().getAllRepositories().observe(owner, Observer {
				val repos = it as ArrayList
				val expiryTime = App.getContext().defaultSharedPreferences.getLong(PREF_KEY_DATA_EXPIRE_TIME, 0)
				if (repos.isNotEmpty() && System.currentTimeMillis() < expiryTime) {
					liveData.value = Triple(Utils.getString(R.string.err_empty_response), Utils.getString(R.string.err_msg_page_not_found), repos)
				} else {
					trendingRepositoryRouter.init()
				}
			})
		}
	}
	
	/**
	 * Cancel the ongoing call.
	 */
	fun stop() {
		trendingRepositoryRouter.stop()
	}
	
	override fun onCallSuccess(response: ArrayList<GitHubRepo>) {
		InsertOperationAsync().execute(response)
	}
	
	override fun onCallFail(
			cause: String,
			throwable: Throwable,
			responseBody: ResponseBody?
	) {
		liveData.value = Triple(cause, throwable.localizedMessage, arrayListOf())
	}
	
	private class InsertOperationAsync : AsyncTask<ArrayList<GitHubRepo>, Unit, Unit>() {
		
		override fun doInBackground(vararg args: ArrayList<GitHubRepo>) {
			val response = args[0]
			val preferences = App.getContext().defaultSharedPreferences
			preferences.edit().putLong(PREF_KEY_DATA_EXPIRE_TIME, (System.currentTimeMillis() + MILLISECONDS.convert(2, HOURS))).apply()
			App.getAppDatabase().gitHubRepoDao().deleteAllRepositories()
			App.getAppDatabase().gitHubRepoDao().insertAllRepositories(response)
			TrendingRepository.getLiveData()
					.postValue(Triple(Utils.getString(R.string.err_empty_response), Utils.getString(R.string.err_msg_page_not_found), response))
		}
	}
}