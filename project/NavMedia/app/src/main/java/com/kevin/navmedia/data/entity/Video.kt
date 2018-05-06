package com.kevin.navmedia.data.entity

/**
 * Created by quf93 on 2018-05-07.
 */
data class Video constructor( val id : String,
                  val thumbnailUrl : String,
                  val videoUrl : String,
                  val time : String,
                  val title : String,
                  val playCount : Int,
                  val loveCount : Int,
                  // val comments : List<Comment>?,
                  val order : Int,
                  val rank : Int,
                  val rankVar : Int,
                  val program : String // test
                  // val programId: String,
                  // val playlistId: String
)