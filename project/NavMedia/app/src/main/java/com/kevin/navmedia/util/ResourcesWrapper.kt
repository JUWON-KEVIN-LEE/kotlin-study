package com.kevin.navmedia.util

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * Created by quf93 on 2018-04-18.
 */
class ResourcesWrapper(val context: Context) {

    fun getString(resId: Int): String = context.getString(resId)

    fun getColor(resId: Int): Int = ContextCompat.getColor(context, resId)
}