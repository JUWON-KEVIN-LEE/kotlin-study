package com.kevin.navmedia.ui.video

import android.app.Dialog
import android.view.ViewGroup
import com.google.android.exoplayer2.ui.PlayerView

/**
 * Created by quf93 on 2018-04-16.
 */
class PlayerUtil {
    companion object {
        /**
         * Make full size of screen
         */
        fun fullScreen(playerView: PlayerView, fullScreen: Dialog): Boolean {
            (playerView.parent as ViewGroup).removeView(playerView)
            fullScreen.addContentView(playerView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
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