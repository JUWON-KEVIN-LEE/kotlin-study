package com.juwon_lee.navmedia.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.juwon_lee.navmedia.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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