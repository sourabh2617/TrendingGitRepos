package com.sourabhapps.trendinggitrepos.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sourabhapps.trendinggitrepos.utilities.AUTHOR
import com.sourabhapps.trendinggitrepos.utilities.AVATAR
import com.sourabhapps.trendinggitrepos.utilities.BUILT_BY
import com.sourabhapps.trendinggitrepos.utilities.CURRENT_PERIOD_STARS
import com.sourabhapps.trendinggitrepos.utilities.DESCRIPTION
import com.sourabhapps.trendinggitrepos.utilities.FORKS
import com.sourabhapps.trendinggitrepos.utilities.LANGUAGE
import com.sourabhapps.trendinggitrepos.utilities.LANGUAGE_COLOR
import com.sourabhapps.trendinggitrepos.utilities.NAME
import com.sourabhapps.trendinggitrepos.utilities.STARTS
import com.sourabhapps.trendinggitrepos.utilities.TABLE_GITHUB_REPO
import com.sourabhapps.trendinggitrepos.utilities.URL

@Entity(tableName = TABLE_GITHUB_REPO)
data class GitHubRepo(
		@PrimaryKey(autoGenerate = true) var id: Int = 0,
		
		@ColumnInfo @SerializedName(AUTHOR) var author: String? = null,
		
		@ColumnInfo @SerializedName(NAME) var name: String? = null,
		
		@ColumnInfo @SerializedName(AVATAR) var avatar: String? = null,
		
		@ColumnInfo @SerializedName(URL) var url: String? = null,
		
		@ColumnInfo @SerializedName(DESCRIPTION) var description: String? = null,
		
		@ColumnInfo @SerializedName(LANGUAGE) var language: String? = null,
		
		@ColumnInfo @SerializedName(LANGUAGE_COLOR) var languageColor: String? = null,
		
		@ColumnInfo @SerializedName(STARTS) var stars: Long = 0,
		
		@ColumnInfo @SerializedName(FORKS) var forks: Long = 0,
		
		@ColumnInfo @SerializedName(CURRENT_PERIOD_STARS) var currentPeriodStars: Long = 0,
		
		@Ignore @SerializedName(BUILT_BY) var builtBy: ArrayList<BuiltBy> = arrayListOf()
)