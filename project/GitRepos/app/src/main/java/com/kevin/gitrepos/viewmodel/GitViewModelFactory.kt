package com.kevin.gitrepos.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kevin.gitrepos.data.repository.GitReposRepository

/**
 * Created by quf93 on 2018-04-17.
 */
class GitViewModelFactory(private val repository: GitReposRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReposViewModel::class.java)) {
            return ReposViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}