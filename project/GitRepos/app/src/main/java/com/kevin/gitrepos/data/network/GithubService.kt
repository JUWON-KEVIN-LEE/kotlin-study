package com.kevin.gitrepos.data.network

import com.kevin.gitrepos.data.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by quf93 on 2018-04-17.
 */
interface GithubService {

    @GET("/users/{username}/repos")
    fun getRepos(@Path("username") username: String,
                 @Query("page") page: Int = 1,
                 @Query("per_page") perPage: Int = 30): Call<List<Repo>>
}