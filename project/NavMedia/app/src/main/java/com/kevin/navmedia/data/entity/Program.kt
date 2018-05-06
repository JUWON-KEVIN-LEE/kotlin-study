package com.kevin.navmedia.data.entity

/**
 * Created by quf93 on 2018-05-07.
 */
data class Program(val id : String,
                   val info : ProgramInfo,
                   val playlist : List<Playlist>)