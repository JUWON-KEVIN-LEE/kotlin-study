package com.kevin.navmedia.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kevin.navmedia.R
import com.kevin.navmedia.ui.main.adapter.NPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = NPagerAdapter(supportFragmentManager)
        tab.setViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}