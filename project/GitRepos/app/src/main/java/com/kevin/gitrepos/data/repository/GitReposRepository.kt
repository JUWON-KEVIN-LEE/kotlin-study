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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-17.
 */
@Singleton
class GitReposRepository(private val executors: AppExecutors,
                         private val database: AppDatabase,
                         private val service: GithubService) {

    private val repos = MediatorLiveData<Resource<List<Repo>>>()
    private val more = MutableLiveData<Resource<List<Repo>>>()
    private var nextPage: Int = 2
    private var endReached: Boolean = false

    companion object {
        const val PER_PAGE = 30
    }

    fun getRepos(username: String): LiveData<Resource<List<Repo>>> {
        nextPage = 2
        endReached = false
        repos.value = Resource(status = Status.LOADING)

        val dbSource = loadFromDB(username)

        repos.addSource(dbSource, { data ->
            repos.removeSource(dbSource)
            if(!shouldFetch(data)) repos.value = Resource(status = Status.SUCCESS, data = data)
            else loadFromNetwork(username)
        })

        return repos
    }

    fun getReposMore(username: String): LiveData<Resource<List<Repo>>> {
        if(endReached) {
            more.value = Resource(status = Status.EMPTY)
            return more
        }

        more.value = Resource(status = Status.LOADING)
        executors.networkIO.execute {
            service.getRepos(username, nextPage, PER_PAGE)
                    .enqueue(object: Callback<List<Repo>> {
                        override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                            more.value = Resource(status = Status.ERROR)
                        }

                        override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                            if(response?.isSuccessful == true) {
                                response.body()?.let {
                                    more.value = Resource(status = Status.SUCCESS, data = it)
                                    if(it.size < PER_PAGE) endReached = true
                                    else nextPage++
                                }
                            } else more.value = Resource(status = Status.ERROR)
                        }
                    })
        }
        return more
    }

    private fun loadFromNetwork(username: String) {
        val source = MutableLiveData<List<Repo>>()
        repos.addSource(source, { data ->
            repos.removeSource(source)
            if(data != null) {
                executors.diskIO.execute{ saveToDB(data) }
                repos.value = Resource(status = Status.SUCCESS, data = data)
            } else repos.value = Resource(status = Status.ERROR)
        })

        executors.networkIO.execute{
            service.getRepos(username, 1, PER_PAGE)
                    .enqueue(object : Callback<List<Repo>> {
                        override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                            source.value = null
                        }

                        override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                            if(response?.isSuccessful == true) {
                                source.value = response.body()
                                response.body()?.size?.let { if(it < 30) endReached = true }
                            } else source.value = null
                        }
                    })
        }
    }

    private fun loadFromDB(username: String): LiveData<List<Repo>> {
        return database.repoDao().selectByName(username)
    }

    private fun saveToDB(data: List<Repo>) {
        data.map { it.last_inserted = System.currentTimeMillis() }
        database.repoDao().insert(data)
    }

    private fun shouldFetch(data: List<Repo>?): Boolean {
        if(data == null || data.isEmpty()) return true

        return false
    }


}