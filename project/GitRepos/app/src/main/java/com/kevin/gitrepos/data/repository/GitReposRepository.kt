package com.kevin.gitrepos.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.kevin.gitrepos.data.db.AppDatabase
import com.kevin.gitrepos.data.model.Repo
import com.kevin.gitrepos.data.model.Resource
import com.kevin.gitrepos.data.model.Status
import com.kevin.gitrepos.data.network.GithubService
import com.kevin.gitrepos.util.AppExecutors

/**
 * Created by quf93 on 2018-04-17.
 */
class GitReposRepository(private val executors: AppExecutors,
                         private val database: AppDatabase,
                         private val service: GithubService) {

    private val repos = MediatorLiveData<Resource<List<Repo>>>()
    private val more = MutableLiveData<Resource<List<Repo>>>()
    private var nextPage: Int = 2
    private var endReached: Boolean = false

    fun getReposByUsername(username: String): LiveData<Resource<List<Repo>>> {
        nextPage = 2
        endReached = false

        repos.value = Resource(status = Status.SUCCESS)
        val dbSource = loadFromDB(username)
        repos.addSource(dbSource, { data ->
            repos.removeSource(dbSource)
            if(!shouldFetch(data)) repos.value = Resource(status = Status.SUCCESS, data = data)
            else loadFromNetwork(username)
        })


        return repos
    }

    private fun loadFromNetwork(username: String) {

    }

    private fun loadFromDB(username: String): LiveData<List<Repo>> {
        return database.repoDao().selectByName(username)
    }

    private fun shouldFetch(data: List<Repo>?): Boolean {
        if(data == null || data.isEmpty()) return true

        return false
    }
}