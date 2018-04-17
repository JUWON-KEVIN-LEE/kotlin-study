package com.kevin.gitrepos.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by quf93 on 2018-04-17.
 */
@Entity(tableName = "repo")
data class Repo(@Json(name = "id") @PrimaryKey val id: String,
                @Json(name = "name") val name: String,
                @Json(name = "language") val language: String,
                @Json(name = "description") val description: String,
                @Json(name = "forks_count") val forks_count: Int,
                @Json(name = "watchers_count") val watchers_count: Int,
                @Json(name = "open_issues_count") val open_issues_count: Int,
                @Json(name = "owner") @Embedded val owner: Owner,
                @Json(name = "pushed_at") var pushed_at: String,
                @Json(name = "created_at") val created_at: String,
                @Json(name = "updated_at") val updated_at: String) {

    data class Owner(@Json(name = "login") val login: String,
                     @Json(name = "id") val id: Int,
                     @Json(name = "avatar_url") val avatar_url: String)

}