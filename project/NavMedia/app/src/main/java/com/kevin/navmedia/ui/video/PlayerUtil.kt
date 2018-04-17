package com.kevin.navmedia.ui.video

import android.app.Dialog
import android.view.ViewGroup
import com.google.android.exoplayer2.ui.PlayerView

/**
 * 코틀린에는 정적(Static) 변수 혹은 메소드가 없고,대신 패키지 내에 함수를 선언하여 사용할 수 있습니다.
 * 또는 companion object 를 사용할 수도 있습니다.
 * Created by quf93 on 2018-04-16.
 */
class PlayerUtil {
    companion object {
        /**
         * 풀 스크린
         */
        fun fullScreen(playerView: PlayerView, fullScreen: Dialog): Boolean {
            (playerView.parent as ViewGroup).removeView(playerView)
            fullScreen.addContentView(playerView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            fullScreen.show()

            return true
        }

        /**
         * 원래 사이즈의 스크린
         */
        fun closeFullScreen(playerView: PlayerView, fullScreen: Dialog, parent: ViewGroup): Boolean{
            (playerView.parent as ViewGroup).removeView(playerView)
            parent.addView(playerView)
            fullScreen.dismiss()

            return false
        }
    }
}