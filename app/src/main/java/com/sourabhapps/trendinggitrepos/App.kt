package com.sourabhapps.trendinggitrepos

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.sourabhapps.trendinggitrepos.database.AppDatabase

class App : MultiDexApplication() {
	
	companion object {
		
		private lateinit var app: App
		private lateinit var appDatabase: AppDatabase
		
		/**
		 * @return current context of this application
		 */
		@JvmStatic
		fun getContext(): Context = app.applicationContext
		
		@JvmStatic
		fun getAppDatabase(): AppDatabase = appDatabase
	}
	
	override fun onCreate() {
		super.onCreate()
		app = this
		appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "app-db").build()
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
	}
}