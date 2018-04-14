package com.juwon_lee.bottomnavigation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var items = ArrayList<BottomNavigationItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items.add(BottomNavigationItem("Home", R.drawable.home, null))
        items.add(BottomNavigationItem("Map", R.drawable.map_black, null))
        items.add(BottomNavigationItem("Statistics", R.drawable.graph_black, null))
        items.add(BottomNavigationItem("Setting", R.drawable.setting_black, null))

        navigator.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                navigator.addItems(items)
                navigator.setCurrentItem(0)
                navigator.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }
}
