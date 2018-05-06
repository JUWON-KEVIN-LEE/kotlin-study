package com.kevin.navmedia.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import android.os.Build
import com.kevin.navmedia.BuildConfig

/**
 * Created by quf93 on 2018-05-04.
 */
data class Device (
        @PrimaryKey
        @ColumnInfo(name = "device_id")
        val deviceId: String,

        val model: String = "${Build.BRAND} ${Build.MODEL}",

        val os: String = "android",

        @ColumnInfo(name = "os_version")
        val osVersion: String = Build.VERSION.RELEASE,

        @ColumnInfo(name = "app_version")
        val appVersion: String = BuildConfig.VERSION_NAME,

        @ColumnInfo(name = "user_agent")
        val userAgent: String
)