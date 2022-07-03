package com.sourabhapps.trendinggitrepos.views.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * [TrendingViewModel] instance creator.
 */
class TrendingViewModelFactory : ViewModelProvider.Factory {
	
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(TrendingViewModel::class.java)) {
			return (TrendingViewModel() as T)
		} else {
			throw IllegalArgumentException("Unknown ViewModel class")
		}
	}
}