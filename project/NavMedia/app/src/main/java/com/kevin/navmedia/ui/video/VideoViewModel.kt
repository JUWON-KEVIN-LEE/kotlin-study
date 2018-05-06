package com.kevin.navmedia.ui.video

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import android.view.View

/**
 * Created by quf93 on 2018-04-27.
 */
class VideoViewModel: ViewModel() {

    /**
     *  PlayerView 를 제외한 Others Visibility
     */
    val othersVisibility = ObservableInt()

    init {
        othersVisibility.set(View.VISIBLE)
    }
}