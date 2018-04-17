package com.kevin.navmedia.ui.main.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kevin.navmedia.R
import kotlinx.android.synthetic.main.item_page.view.*

/**
 * Created by quf93 on 2018-04-14.
 */
class ViewPagerAdapter(val context: Context, var items: List<Int>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("JUWONLEE", "instantiateItem: " + position)
        val view = LayoutInflater.from(context).inflate(R.layout.item_page, container, false)

        view.image.setImageResource(items[position])
        view.image.scaleType = ImageView.ScaleType.CENTER_CROP

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}