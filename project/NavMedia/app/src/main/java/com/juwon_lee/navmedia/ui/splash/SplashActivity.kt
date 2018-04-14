package com.juwon_lee.navmedia.ui.splash

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.juwon_lee.navmedia.R
import com.juwon_lee.navmedia.ui.main.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val images = listOf(
            R.drawable.splash_0,
            R.drawable.splash_1,
            R.drawable.splash_2,
            R.drawable.splash_3
    )

    private val summ = listOf(
            R.string.splash_title_first,
            R.string.splash_title_second,
            R.string.splash_title_third,
            R.string.splash_title_fourth
    )

    private val details = listOf(
            R.string.splash_detail_first,
            R.string.splash_detail_second,
            R.string.splash_detail_third,
            R.string.splash_detail_fourth
    )

    var mScrollState: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewPager.adapter = ViewPagerAdapter(this, images)
        viewPager.addOnPageChangeListener(this)
        indicator.setViewPager(viewPager)
    }

    private fun setCurrentItem(position: Int) {
        Log.d("JUWONLEE", "setCurrentItem")
        summary.text = getString(summ[position])
        detail.text = getString(details[position])
    }

    override fun onPageScrollStateChanged(state: Int) {
        Log.d("JUWONLEE", "onPageScrollStateChanged: " + state)
        mScrollState = state
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if(mScrollState == ViewPager.SCROLL_STATE_IDLE) setCurrentItem(position)
    }

    override fun onPageSelected(position: Int) {
        setCurrentItem(position)
    }
}
