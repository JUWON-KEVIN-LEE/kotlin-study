package com.kevin.navmedia.view.exoplayer;

import android.content.Context;
import android.media.AudioManager;

/**
 * NPlayerView 의 Touch event 를 ControlView 를 통해 전달받아
 * 소리나 밝기를 조절
 * Created by quf93 on 2018-04-28.
 */

public class GestureManager {
    private Context context;

    private AudioManager audioManager;

    private int currentVolume;
    private int maxVolume;

    private boolean isMute;
    private boolean isMax;

    public GestureManager(Context context, AudioManager audioManager) {
        this.context = context;
        this.audioManager = audioManager;

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public int calculateVolumeVariance(float percentage) {
        percentage = Math.round(percentage * 100) / 100;
        return (int)(percentage * maxVolume);
    }

    public void setStreamVolume(int variance /* volume variance */) {
        int volume = currentVolume + variance;
        volume = Math.min(Math.max(0, volume), maxVolume);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        isMute = currentVolume == 0;
        isMax = currentVolume == maxVolume;
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
