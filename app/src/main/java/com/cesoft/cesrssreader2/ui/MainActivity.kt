package com.cesoft.cesrssreader2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.ui.feedlist.FeedlistFragment

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)
		if(savedInstanceState == null) {
			supportFragmentManager.beginTransaction()
					.replace(R.id.container, FeedlistFragment.newInstance())
					.commitNow()
		}
	}
}
