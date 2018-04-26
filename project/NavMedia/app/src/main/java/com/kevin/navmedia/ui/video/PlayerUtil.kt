package com.kevin.navmedia.ui.video

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.google.android.exoplayer2.ui.PlayerView

/**
 * Created by quf93 on 2018-04-16.
 */
class PlayerUtil {
    companion object {
        /**
         * Make original size of screen
         */
        fun portrait(activity: Activity, playerView: PlayerView, others: View) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            // playerView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            others.visibility = View.VISIBLE
        }

        /**
         * Make full size of screen
         */
        fun landscape(activity: Activity, playerView: PlayerView, others: View) {
            //0.
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            //1.
            var flag = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                flag = flag or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }

            playerView.systemUiVisibility = flag
            //2.
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            others.visibility = View.GONE
        }
    }
}