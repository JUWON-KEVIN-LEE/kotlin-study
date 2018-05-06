package com.kevin.navmedia.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.kevin.navmedia.R
import kotlinx.android.synthetic.main.tab_main.view.*

/**
 * Created by quf93 on 2018-05-06.
 */
class MainTab: FrameLayout, View.OnClickListener, ViewPager.OnPageChangeListener {

    private var mViewPager : ViewPager? = null

    private var current : Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs) {
        LayoutInflater.from(context).inflate(R.layout.tab_main, this)

        top100.setOnClickListener(this)
        feed.setOnClickListener(this)
        live.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.top100 -> {
                if(current == 0) return
                changeUI(v)
                current = 0
                if(mViewPager?.currentItem != 0) mViewPager?.currentItem = 0
            }
            R.id.feed -> {
                if(current == 1) return
                changeUI(v)
                current = 1
                if(mViewPager?.currentItem != 1) mViewPager?.currentItem = 1
            }
            R.id.live -> {
                if(current == 2) return
                changeUI(v)
                current = 2
                if(mViewPager?.currentItem != 2) mViewPager?.currentItem = 2
            }
        }
    }

    private fun changeUI(next : View?) {
        when(current) {
            0 -> { inactivate(top100) }
            1 -> { inactivate(feed) }
            2 -> { inactivate(live) }
        }

        (next as TextView).setTextColor(ContextCompat.getColor(context, R.color.Aquamarine))
    }

    private fun inactivate(current: TextView) {
        current.setTextColor(ContextCompat.getColor(context, R.color.Black))
    }

    fun setViewPager(viewPager: ViewPager) {
        if(mViewPager == viewPager) return

        if(viewPager.adapter == null) throw IllegalStateException("ViewPager does not have Adapter.")

        mViewPager = viewPager
        mViewPager?.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when(position) {
            0 -> { onClick(top100) }
            1 -> { onClick(feed) }
            2 -> { onClick(live) }
        }
    }
}