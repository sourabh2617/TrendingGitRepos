package com.sourabhapps.trendinggitrepos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sourabhapps.trendinggitrepos.R
import com.sourabhapps.trendinggitrepos.adapters.TrendingRepositoryAdapter.ViewHolder
import com.sourabhapps.trendinggitrepos.models.GitHubRepo
import com.sourabhapps.trendinggitrepos.utilities.NA
import kotlinx.android.synthetic.main.item_trending_repositories_list.view.*

class TrendingRepositoryAdapter : RecyclerView.Adapter<ViewHolder>() {
	
	private var items: ArrayList<GitHubRepo>? = null
	private var expandedPosition: Int = -1
	
	override fun onCreateViewHolder(
			parent: ViewGroup,
			viewType: Int
	): ViewHolder {
		val viewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trending_repositories_list, parent, false))
		viewHolder.itemView.setOnClickListener {
			when {
				this.expandedPosition == -1 -> {
					// No view has expanded
					this.expandedPosition = viewHolder.currentPosition
					notifyItemChanged(this.expandedPosition)
				}
				this.expandedPosition == viewHolder.currentPosition -> {
					// Already expanded and needs to collapse
					this.expandedPosition = -1
					notifyItemChanged(viewHolder.currentPosition)
				}
				else -> {
					// Collapse already expanded item and expand new item
					val alreadyExpandedPosition = this.expandedPosition
					this.expandedPosition = -1
					notifyItemChanged(alreadyExpandedPosition)
					this.expandedPosition = viewHolder.currentPosition
					notifyItemChanged(this.expandedPosition)
				}
			}
		}
		return viewHolder
	}
	
	override fun getItemCount(): Int = items?.size ?: 0
	
	override fun onBindViewHolder(
			holder: ViewHolder,
			position: Int
	) {
		val repo = items?.get(position)
		if (repo != null) {
			holder.setData(repo, position, position == this.expandedPosition)
		}
	}
	
	/**
	 * Set items to be added in list.
	 */
	fun setItems(repos: ArrayList<GitHubRepo>) {
		items = repos
		notifyDataSetChanged()
	}
	
	/**
	 * Sort current list by star count in ascending order.
	 */
	fun sortByStars() {
		if (!items.isNullOrEmpty()) {
			items!!.sortBy {it.stars}
			notifyDataSetChanged()
		}
	}
	
	/**
	 * Sort current list by author name in ascending order.
	 */
	fun sortByName() {
		if (!items.isNullOrEmpty()) {
			items!!.sortBy {it.author}
			notifyDataSetChanged()
		}
	}
	
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		
		var currentPosition = 0
		
		fun setData(
				gitHubRepo: GitHubRepo,
				position: Int,
				isExpanded: Boolean = false
		) {
			this.currentPosition = position
			Glide.with(itemView.context).load(gitHubRepo.avatar).circleCrop().placeholder(R.drawable.ic_user_placeholder)
					.error(R.drawable.ic_user_placeholder).into(this.itemView.authorProfileImage)
			this.itemView.authorNameText.text = gitHubRepo.author ?: NA
			this.itemView.repositoryNameText.text = gitHubRepo.name ?: NA
			this.itemView.descriptionText.text = gitHubRepo.description ?: NA
			this.itemView.languageText.text = gitHubRepo.language ?: NA
			this.itemView.totalStarText.text = gitHubRepo.stars.toString()
			this.itemView.totalForkText.text = gitHubRepo.forks.toString()
			this.itemView.descriptionText.visibility = if (isExpanded) View.VISIBLE else View.GONE
			this.itemView.detailsLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
		}
	}
}