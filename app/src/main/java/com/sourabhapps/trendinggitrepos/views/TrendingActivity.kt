package com.sourabhapps.trendinggitrepos.views

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.sourabhapps.trendinggitrepos.R
import com.sourabhapps.trendinggitrepos.adapters.TrendingRepositoryAdapter
import com.sourabhapps.trendinggitrepos.dagger.components.DaggerTrendingComponent
import com.sourabhapps.trendinggitrepos.models.GitHubRepo
import com.sourabhapps.trendinggitrepos.views.models.TrendingViewModel
import kotlinx.android.synthetic.main.activity_trending.*
import kotlinx.android.synthetic.main.layout_error.view.*
import javax.inject.Inject

class TrendingActivity : AppCompatActivity(), Observer<Triple<String, String, ArrayList<GitHubRepo>>> {
	
	@Inject lateinit var trendingRepositoryAdapter: TrendingRepositoryAdapter
	
	private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
	private lateinit var trendingViewModel: TrendingViewModel
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_trending)
		// Initializing dagger, view model and views.
		val daggerComponent = DaggerTrendingComponent.create()
		daggerComponent.injectTrendingActivity(this)
		trendingViewModel = ViewModelProviders.of(this, daggerComponent.getTrendingViewModelFactory())[TrendingViewModel::class.java]
		initView()
	}
	
	override fun onStart() {
		super.onStart()
		trendingViewModel.getObserver().observe(this, this)
		getTrendingRepositories(false)
	}
	
	override fun onStop() {
		super.onStop()
		trendingViewModel.getObserver().removeObserver(this)
		skeletonScreen.hide()
		trendingViewModel.stopLoadingRepositories()
	}
	
	override fun onChanged(t: Triple<String, String, ArrayList<GitHubRepo>>?) {
		if (t?.third?.isNotEmpty() == true) {
			skeletonScreen.hide()
			trendingSwipeRefresh.isRefreshing = false
			trendingRecyclerView.visibility = View.VISIBLE
			trendingRepositoryAdapter.setItems(t.third)
			errorLayout.visibility = View.GONE
		} else {
			val defaultTitle = getString(R.string.err_something_went_wrong)
			val defaultSubtitle = getString(R.string.err_msg_something_went_wrong)
			showError(t?.first ?: defaultTitle, t?.second ?: defaultSubtitle)
		}
	}
	
	/**
	 * Initializing all view components.
	 */
	private fun initView() {
		setSupportActionBar(toolbar)
		trendingRecyclerView.layoutManager = LinearLayoutManager(this)
		trendingRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
		trendingSwipeRefresh.setOnRefreshListener {
			trendingSwipeRefresh.isRefreshing = true
			getTrendingRepositories(false, forceUpdate = true)
		}
		errorLayout.errorRetryBtn.setOnClickListener {getTrendingRepositories(true)}
		skeletonScreen = Skeleton.bind(trendingRecyclerView).adapter(trendingRepositoryAdapter).load(R.layout.item_trending_repositories_skeleton)
				.duration(1300).show()
	}
	
	/**
	 * Show no data layout with inputted error message and hide other views.
	 *
	 * @param errorTitle to be display.
	 * @param errorSubtitle to be display.
	 */
	private fun showError(
			errorTitle: String,
			errorSubtitle: String
	) {
		skeletonScreen.hide()
		trendingSwipeRefresh.isRefreshing = false
		trendingRecyclerView.visibility = View.GONE
		errorLayout.visibility = View.VISIBLE
		errorLayout.errorPrimaryText.text = errorTitle
		errorLayout.errorSecondaryText.text = errorSubtitle
	}
	
	/**
	 * Request trending repositories.
	 */
	private fun getTrendingRepositories(
			showSkeleton: Boolean,
			forceUpdate: Boolean = false
	) {
		if (showSkeleton) {
			skeletonScreen.show()
			trendingSwipeRefresh.isRefreshing = false
			trendingRecyclerView.visibility = View.VISIBLE
		}
		errorLayout.visibility = View.GONE
		trendingViewModel.getRepositories(forceUpdate, this)
	}
}