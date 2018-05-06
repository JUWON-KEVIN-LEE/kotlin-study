package com.kevin.navmedia.data.entity

/**
 * Created by quf93 on 2018-05-07.
 */
data class Comment (val id : String,
                    val content : String,
                    val nickname : String,
                    val date : String,
                    val likeCount : Int,
                    val dislikeCount : Int,
                    val replies : List<Comment>)