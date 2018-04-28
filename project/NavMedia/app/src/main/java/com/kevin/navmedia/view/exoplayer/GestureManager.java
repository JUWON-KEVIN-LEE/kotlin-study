package com.kevin.navmedia.view.exoplayer;

import android.view.MotionEvent;
import android.view.View;

/**
 * NPlayerView 의 Touch event 를 ControlView 를 통해 전달받아
 * 소리나 밝기를 조절
 * Created by quf93 on 2018-04-28.
 */

public class GestureManager implements View.OnTouchListener {



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
