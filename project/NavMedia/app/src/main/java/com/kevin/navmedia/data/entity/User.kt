package com.kevin.navmedia.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by quf93 on 2018-05-04.
 */
@Entity
data class User(@PrimaryKey val id: String,
                val email: String,
                val name: String,
                val age: String,
                val gender: String)