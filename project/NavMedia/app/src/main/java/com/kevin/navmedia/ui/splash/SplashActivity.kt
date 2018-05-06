package com.kevin.navmedia.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.kevin.navmedia.R
import com.kevin.navmedia.ui.main.adapter.ViewPagerAdapter
import com.kevin.navmedia.ui.video.VideoActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val colors = listOf(
            R.color.LightGreen,
            R.color.LightYellow,
            R.color.LightSalmon,
            R.color.LightCyan
    )

    private val summaries = listOf(
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

        viewPager.adapter = ViewPagerAdapter(this, colors)
        viewPager.addOnPageChangeListener(this)
        indicator.setViewPager(viewPager)

        start.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
            finish()
        }
    }

    private fun setCurrentItem(position: Int) {
        summary.visibility = View.INVISIBLE
        detail.visibility = View.INVISIBLE

        summary.text = getString(summaries[position])
        detail.text = getString(details[position])

        summary.visibility = View.VISIBLE
        detail.visibility = View.VISIBLE

        if(position == 3) {
            indicator.visibility = View.GONE
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
