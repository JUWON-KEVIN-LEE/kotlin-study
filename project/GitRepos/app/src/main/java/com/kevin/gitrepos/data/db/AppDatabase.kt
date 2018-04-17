package com.kevin.gitrepos.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.kevin.gitrepos.data.model.Repo

/**
 * Created by quf93 on 2018-04-17.
 */
@Database(entities = [(Repo::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun repoDao(): RepoDao

}