package com.kevin.gitrepos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.kevin.gitrepos.data.model.Repo
import com.kevin.gitrepos.data.model.Resource
import com.kevin.gitrepos.data.repository.GitReposRepository

/**
 * Created by quf93 on 2018-04-17.
 */
class ReposViewModel(private val repository: GitReposRepository) : ViewModel() {

    val usernameLiveData = MutableLiveData<String>()
    val loadMoreData = MutableLiveData<Boolean>()
    var reposLiveData: LiveData<Resource<List<Repo>>>
    var moreReposLiveData: LiveData<Resource<List<Repo>>?>

    init {
        loadMoreData.value = false
        reposLiveData = Transformations.switchMap(usernameLiveData,
                { username -> repository.getRepos(username)})
        moreReposLiveData = Transformations.switchMap(loadMoreData,
                { loadMore ->
                    if(loadMore) usernameLiveData.value?.let { repository.getReposMore(it) }
                    else null
                })
    }
}