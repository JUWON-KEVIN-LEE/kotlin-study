package com.kevin.navmedia.ui.video

import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.exoplayer2.ui.PlayerView

/**
 * Created by quf93 on 2018-04-16.
 */
class PlayerUtil {
    companion object {
        fun hideSystemUI(playerView: PlayerView) {
            playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }

        fun showSystemUI(playerView: PlayerView) {
            playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }

        /**
         * Make full size of screen
         */
        fun fullScreen(playerView: PlayerView, controlView: ViewGroup, fullScreen: Dialog): Boolean {
            (playerView.parent as ViewGroup).removeView(playerView)
            (playerView.parent as ViewGroup).removeView(controlView)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            fullScreen.addContentView(playerView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            fullScreen.addContentView(controlView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            fullScreen.show()

            return true // Is this view full size of screen ?
        }

        /**
         * Make original size of screen
         */
        fun closeFullScreen(playerView: PlayerView, fullScreen: Dialog, parent: ViewGroup): Boolean{
            (playerView.parent as ViewGroup).removeView(playerView)
            parent.addView(playerView)
            fullScreen.dismiss()

            return false
        }
    }
}