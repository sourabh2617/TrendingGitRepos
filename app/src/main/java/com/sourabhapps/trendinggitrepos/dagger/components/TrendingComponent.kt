package com.sourabhapps.trendinggitrepos.dagger.components

import com.sourabhapps.trendinggitrepos.adapters.TrendingRepositoryAdapter
import com.sourabhapps.trendinggitrepos.dagger.modules.TrendingModule
import com.sourabhapps.trendinggitrepos.views.TrendingActivity
import com.sourabhapps.trendinggitrepos.views.models.TrendingViewModelFactory
import dagger.Component

/**
 * Dagger component class to provide required dependencies.
 */
@Component(modules = [TrendingModule::class])
interface TrendingComponent {
	
	/**
	 * Provide [TrendingRepositoryAdapter] instance.
	 */
	fun getTrendingRepositoryAdapter(): TrendingRepositoryAdapter
	
	/**
	 * Provide [TrendingViewModelFactory] instance.
	 */
	fun getTrendingViewModelFactory(): TrendingViewModelFactory
	
	/**
	 * Inject [TrendingActivity] instance into dagger implementation.
	 */
	fun injectTrendingActivity(trendingActivity: TrendingActivity)
}