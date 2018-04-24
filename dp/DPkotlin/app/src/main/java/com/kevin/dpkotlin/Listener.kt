package com.kevin.dpkotlin

import kotlin.properties.Delegates

/**
 * Created by quf93 on 2018-04-24.
 */
interface TextChangedListener {
    fun onTextChanged(newText: String)
}

class PrintTextChangedListener: TextChangedListener {
    override fun onTextChanged(newText: String) = println("Text is changed to: $newText")
}

class TextView {
    var listener: TextChangedListener? = null

    var text: String by Delegates.observable("") { prop, old, new ->
        listener?.onTextChanged(new)
    }
}

fun main(args: ArrayList<String>) {
    val textview = TextView()
    textview.listener = PrintTextChangedListener()
    textview.text = "hello"
    textview.text = "buy"
}