package com.kevin.navmedia.view.exoplayer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by quf93 on 2018-04-28.
 */

public interface GestureListener {
    int VOLUME_DOWN = -1;
    int VOLUME_MUTE = 0;
    int VOLUME_UP = 1;

    @IntDef({VOLUME_DOWN, VOLUME_MUTE, VOLUME_UP})
    @Retention(RetentionPolicy.SOURCE)
    @interface VolumeChangeType {
    }


}
