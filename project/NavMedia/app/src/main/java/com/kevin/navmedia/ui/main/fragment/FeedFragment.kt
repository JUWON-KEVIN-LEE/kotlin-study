package com.kevin.navmedia.ui.main.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevin.navmedia.R


/**
 * A simple [Fragment] subclass.
 */
class FeedFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

}// Required empty public constructor
