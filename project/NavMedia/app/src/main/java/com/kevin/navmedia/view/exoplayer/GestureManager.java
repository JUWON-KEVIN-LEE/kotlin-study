package com.kevin.navmedia.view.exoplayer;

import android.content.Context;
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.kevin.navmedia.view.exoplayer.NPlaybackControlView.OnOrientationChangedListener;

import static com.kevin.navmedia.view.exoplayer.NPlaybackControlView.OnOrientationChangedListener.LANDSCAPE;
import static com.kevin.navmedia.view.exoplayer.NPlaybackControlView.OnOrientationChangedListener.PORTRAIT;

/**
 * NPlayerView 의 Touch event 를 ControlView 를 통해 전달받아
 * 소리나 밝기를 조절
 * Created by quf93 on 2018-04-28.
 */

public class GestureManager {

    private static final int playerViewDP = 240;

    private final Context context;

    private final DisplayMetrics screen;
    private int yDisplayRange = 0;

    private final AudioManager audioManager;

    private int currentVolume;
    private int maxVolume;

    private boolean isMute;
    private boolean isMax;

    private GestureListener gestureListener;

    public GestureManager(Context context, AudioManager audioManager, DisplayMetrics screen) {
        this.context = context;
        this.audioManager = audioManager;

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        this.screen = screen;
        accessDisplayMetrics(this.screen);
    }

    private void accessDisplayMetrics(DisplayMetrics screen) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(screen);

        if(yDisplayRange == 0) setYDisplayRange(screen.widthPixels, screen.heightPixels);
    }

    private int dpToPixel(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, screen);
    }

    private void setYDisplayRange(int widthPixels, int heightPixels) {
        yDisplayRange = Math.min(dpToPixel(playerViewDP), Math.min(widthPixels, heightPixels));
    }

    private void setYDisplayRange(@OnOrientationChangedListener.OrientationType int orientation) {
        switch (orientation) {
            case PORTRAIT:
                yDisplayRange = dpToPixel(240);
                break;
            case LANDSCAPE:
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
}
