package com.kevin.gitrepos.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.kevin.gitrepos.data.model.Repo

/**
 * Created by quf93 on 2018-04-17.
 */
@Dao
interface RepoDao {

    @Query("SELECT * FROM repo WHERE upper(login) = upper(:name)")
    fun selectByName(name: String): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<Repo>)
}