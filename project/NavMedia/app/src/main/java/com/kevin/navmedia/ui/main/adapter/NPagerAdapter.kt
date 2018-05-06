package com.kevin.navmedia.ui.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kevin.navmedia.ui.main.fragment.FeedFragment
import com.kevin.navmedia.ui.main.fragment.LiveFragment
import com.kevin.navmedia.ui.main.fragment.Top100Fragment

/**
 * Created by quf93 on 2018-05-06.
 */
class NPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {

    // TODO DI
    private val topFragment : Fragment = Top100Fragment()
    private val feedFragment : Fragment = FeedFragment()
    private val liveFragment : Fragment = LiveFragment()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> topFragment
            1 -> feedFragment
            2 -> liveFragment
            else -> throw IllegalStateException("Error on pager adapter getItem(position : Int).")
        }
    }

    override fun getCount(): Int {
        return FRAG_NUM
    }

    companion object {
        const val FRAG_NUM : Int = 3
    }
}