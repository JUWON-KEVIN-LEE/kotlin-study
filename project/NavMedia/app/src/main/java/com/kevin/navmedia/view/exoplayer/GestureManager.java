package com.kevin.navmedia.view.exoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * NPlayerView 의 Touch event 를 ControlView 를 통해 전달받아
 * 소리나 밝기를 조절
 * Created by quf93 on 2018-04-28.
 */

public class GestureManager implements View.OnTouchListener {

    private static final int ORIGIN_HEIGHT_DP = 240;

    private final Context context;

    private final DisplayMetrics screen;
    private int yDisplayRange = 0;

    private final AudioManager audioManager;

    private int currentVolume;
    private int maxVolume;

    private boolean isMute;
    private boolean isMax;

    private GestureListener gestureListener;

    private static final int NONE = 0;
    private static final int VOLUME = 1;
    private static final int BRIGHTNESS = 2;
    private static final int SEEK = 3;

    private int touchAction = NONE;

    private float initX = -1f;
    private float initY = -1f;

    private float touchX = -1f;
    private float touchY = -1f;

    // context <= VideoActivity
    public GestureManager(Context context, AudioManager audioManager, DisplayMetrics screen) {
        this.context = context;
        this.audioManager = audioManager;

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        this.screen = screen;
        accessDisplayMetrics(this.screen);

        if(context instanceof Activity) {
            Activity activity = (Activity) context;
            measureYDisplay(activity.getResources().getConfiguration().orientation);
        }
    }

    private void accessDisplayMetrics(DisplayMetrics screen) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(screen);
    }

    private int calculateYDisplayRange(int dp) {
        // dp to pixel
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, screen);
    }

    private int calculateYDisplayRange(int widthPixels, int heightPixels) {
        return Math.min(widthPixels, heightPixels);
    }

    public void measureYDisplay(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                yDisplayRange = calculateYDisplayRange(ORIGIN_HEIGHT_DP);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                yDisplayRange = calculateYDisplayRange(screen.widthPixels, screen.heightPixels);
                break;
        }
    }

    private int calculateVolumeVariance(float percentage) {
        percentage = Math.round(percentage * 100) / 100;
        return (int)(percentage * maxVolume);
    }

    public void setStreamVolume(float percentage/* 변화율 */) {
        int variance = calculateVolumeVariance(percentage);
        int volume = currentVolume + variance;
        volume = Math.min(Math.max(0, volume), maxVolume);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        isMute = currentVolume == 0;
        isMax = currentVolume == maxVolume;
    }

    public void setGestureListener(GestureListener gestureListener) {
        this.gestureListener = gestureListener;
    }

    public void seekTo(long value) {
        if(gestureListener != null) {
            gestureListener.seekTo(value);
        }
    }

    public DisplayMetrics getScreen() {
        return screen;
    }

    public int getYDisplayRange() {
        return yDisplayRange;
    }

    public int getMaxVolume() {
        return maxVolume;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public boolean isMute() {
        return isMute;
    }

    public boolean isMax() {
        return isMax;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x_var;
        float y_var;

        if(touchX != -1 && touchY != -1) {
            x_var = event.getRawX() - touchX;
            y_var = event.getRawY() - touchY;
        } else {
            x_var = 0f;
            y_var = 0f;
        }

        float coef = Math.abs(y_var / x_var);

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touchAction = NONE;
                touchX = initX = event.getRawX();
                touchY = initY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(touchAction != SEEK && coef > 2) {
                    if(Math.abs(y_var / yDisplayRange) < 0.01) return false;

                    touchX = event.getRawX();
                    touchY = event.getRawY();

                    // Change volume or brightness
                    if((int)initX > (screen.widthPixels / 2)) {
                        setStreamVolume(y_var / yDisplayRange);
                        // showVolumeUI(getCurrentVolume(), isMute());
                    } else {


                    }
                } else {
                    // Do ff or rw
                    seekTo(0);
                }
                break;
            case MotionEvent.ACTION_UP:
                touchX = -1f;
                touchY = -1f;
                break;
            default:
                break;
        }

        return false;
    }
}
