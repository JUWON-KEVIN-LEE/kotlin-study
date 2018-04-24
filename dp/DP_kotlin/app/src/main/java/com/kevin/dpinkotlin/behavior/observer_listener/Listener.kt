package com.kevin.dpinkotlin.behavior.observer_listener

import android.util.Log

/**
 * Created by quf93 on 2018-04-24.
 */
interface TextChangedListener {
    fun onTextChanged(newText: String)
}

class PrintTextChangedListener: TextChangedListener {
    override fun onTextChanged(newText: String) { Log.d("JUWONLEE", "This is changed to $newText")}
}

class TextView {
    var listener: TextChangedListener? = null

    var text: String
}