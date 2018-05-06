package com.kevin.navmedia.data.entity

/**
 * Created by quf93 on 2018-05-07.
 */
data class Playlist (val id : String,
                     val title : String,
                     val size : Int,
                     val videoList : List<Video>)