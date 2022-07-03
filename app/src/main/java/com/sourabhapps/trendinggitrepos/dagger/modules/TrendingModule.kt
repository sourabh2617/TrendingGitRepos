package com.sourabhapps.trendinggitrepos.dagger.modules

import com.sourabhapps.trendinggitrepos.adapters.TrendingRepositoryAdapter
import com.sourabhapps.trendinggitrepos.views.models.TrendingViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TrendingModule {
	
	/**
	 * Provide [TrendingRepositoryAdapter] instance.
	 */
	@Provides
	fun provideTrendingRepositoryAdapter(): TrendingRepositoryAdapter = TrendingRepositoryAdapter()
	
	/**
	 * Provide [TrendingViewModelFactory] instance.
	 */
	@Provides
	fun provideTrendingViewModelFactory(): TrendingViewModelFactory = TrendingViewModelFactory()
}