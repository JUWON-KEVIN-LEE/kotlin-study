package com.kevin.navmedia.view.indicator

import android.support.v4.view.ViewPager

/**
 * Created by quf93 on 2018-04-14.
 */
interface PageIndicator: ViewPager.OnPageChangeListener {

    fun setViewPager(viewPager: ViewPager)

    fun setCurrentPage(currentPage: Int)

    override fun onPageScrollStateChanged(state: Int)

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)

    override fun onPageSelected(position: Int)
}