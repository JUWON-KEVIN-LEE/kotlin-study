package com.juwon_lee.navmedia.ui.splash

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

/**
 * Created by quf93 on 2018-04-14.
 */
data class SplashItem(@DrawableRes
                      val imageRes: Int,
                      @StringRes
                      val titleRes: Int,
                      @StringRes
                      val detailRes: Int)