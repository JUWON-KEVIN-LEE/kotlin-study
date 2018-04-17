package com.kevin.navmedia.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.kevin.navmedia.R
import com.kevin.navmedia.ui.main.MainActivity
import com.kevin.navmedia.ui.main.adapter.ViewPagerAdapter
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

    private var mScrollState: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewPager.adapter = ViewPagerAdapter(this, images)
        viewPager.addOnPageChangeListener(this)
        indicator.setViewPager(viewPager)

        start.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

    }

    private fun setCurrentItem(position: Int) {
        summary.visibility = View.INVISIBLE
        detail.visibility = View.INVISIBLE

        summary.text = getString(summ[position])
        detail.text = getString(details[position])

        summary.visibility = View.VISIBLE
        detail.visibility = View.VISIBLE

        if(position == 3) {
            indicator.visibility = View.INVISIBLE
            start.visibility = View.VISIBLE
        } else {
            indicator.visibility = View.VISIBLE
            start.visibility = View.GONE
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        mScrollState = state
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if(mScrollState == ViewPager.SCROLL_STATE_IDLE) setCurrentItem(position)
    }

    override fun onPageSelected(position: Int) {
        setCurrentItem(position)
    }
}
