package com.sourabhapps.trendinggitrepos.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sourabhapps.trendinggitrepos.R.layout

class SplashActivity : AppCompatActivity(), Runnable {
	
	private val handler: Handler = Handler()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.activity_splash)
	}
	
	override fun onStart() {
		super.onStart()
		handler.postDelayed(this, 2000)
	}
	
	override fun onStop() {
		super.onStop()
		handler.removeCallbacks(this)
	}
	
	override fun run() {
		startActivity(Intent(this, TrendingActivity::class.java))
		finish()
	}
}