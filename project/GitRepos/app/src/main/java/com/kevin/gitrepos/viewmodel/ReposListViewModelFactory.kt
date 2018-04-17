package com.kevin.gitrepos.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kevin.gitrepos.data.repository.GitReposRepository

/**
 * Created by quf93 on 2018-04-17.
 */
class ReposListViewModelFactory(private val repository: GitReposRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReposListViewModel::class.java)) {
            return ReposListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}