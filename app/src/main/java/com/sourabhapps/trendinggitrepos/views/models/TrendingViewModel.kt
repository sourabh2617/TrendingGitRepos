package com.sourabhapps.trendinggitrepos.views.models

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sourabhapps.trendinggitrepos.models.GitHubRepo
import com.sourabhapps.trendinggitrepos.repositories.TrendingRepository

class TrendingViewModel : ViewModel() {
	
	/**
	 * GitHubRepo data observer.GitHubRepo data observer instance created by [TrendingRepository].
	 */
	private val liveData: MutableLiveData<Triple<String, String, ArrayList<GitHubRepo>>> = TrendingRepository.getLiveData()
	
	/**
	 * Get all GitHub's trending repositories.
	 */
	fun getRepositories(
			forceUpdate: Boolean,
			owner: LifecycleOwner
	) {
		TrendingRepository.getRepositories(forceUpdate, owner)
	}
	
	/**
	 * Cancel any ongoing network calls.
	 */
	fun stopLoadingRepositories() {
		TrendingRepository.stop()
	}
	
	/**
	 * @return [MutableLiveData] instance which will observe repositories.
	 */
	fun getObserver() = liveData
}